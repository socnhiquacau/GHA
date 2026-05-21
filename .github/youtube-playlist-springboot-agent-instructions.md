# Project Instructions: YouTube-like Playlist Backend with Spring Boot, gRPC, Protobuf, Docker, and Minikube

## 1. Project Goal

Build a backend system that simulates the playlist and video aggregation features of a YouTube-like platform.

The system must support:

1. **User-generated Playlists**
   - User-created playlists
   - Watch Later playlist
   - History playlist

2. **System-generated Playlists / Aggregations**
   - Trending videos
   - Genre-based playlists
   - Weekly hot videos
   - Other automatically generated video collections

The system should be designed as a **Spring Boot microservice-style multi-module project**.

Each business domain must be separated into its own module/service. Services communicate internally through **gRPC** using existing or generated **Protocol Buffers** contracts.

Authentication is **not implemented inside this project**. Authentication is handled by an external **Gateway source**. The Gateway will call REST APIs exposed by these services and pass authenticated user information through trusted HTTP headers.

---

## 2. Core Architecture

Expected high-level architecture:

```text
[Client App]
    |
    v
[External Gateway / API Gateway]
    |
    | REST + trusted headers
    |
    +-----------------------------+
    |                             |
    v                             v
[video-service]              [playlist-service]
    ^                             ^
    | gRPC                        | gRPC
    |                             |
    +-------------+---------------+
                  |
                  v
        [aggregation-worker]
```

Main rules:

```text
Gateway -> Business services: REST
Business service -> Business service: gRPC
Worker -> Business services: gRPC
Authentication: handled by Gateway
Authorization/business permission: handled by business services
```

---

## 3. Technology Stack

Use the following stack:

```text
Java 17+
Spring Boot 3.x
Spring Web
Spring Data JPA
Spring Validation
PostgreSQL
gRPC
Protocol Buffers
Lombok
MapStruct
Maven multi-module
Docker
Kubernetes
Minikube
```

Optional technologies:

```text
Spring Scheduler
RabbitMQ / Kafka
Redis
Flyway / Liquibase
Testcontainers
Spring Actuator
```

Do not introduce unnecessary frameworks unless they clearly improve the implementation.

---

## 4. Authentication and Gateway Rule

Authentication is handled by an external Gateway.

Do **not** implement:

```text
- Login API
- Register API
- JWT issuing
- JWT refresh token
- Password hashing
- AuthService module
- User password table
```

Business services must trust the Gateway and read authenticated user context from headers.

Expected headers:

```http
X-User-Id: 1001
X-User-Role: USER
X-Request-Id: abc-xyz
```

Rules:

```text
- If an API requires login and X-User-Id is missing, return 401 Unauthorized.
- If X-User-Id exists but the user does not have permission, return 403 Forbidden.
- Services may use X-User-Role for simple authorization checks.
- Do not verify JWT inside video-service or playlist-service.
- Do not put authentication logic inside business services.
```

Example roles:

```text
USER
ADMIN
SYSTEM
```

For internal gRPC calls, service-to-service calls may use internal metadata such as:

```text
x-internal-service: aggregation-worker
x-request-id: abc-xyz
```

System-level update APIs exposed through gRPC should only be used by internal services such as `aggregation-worker`.

---

## 5. Required Maven Multi-module Structure

Recommended root structure:

```text
youtube-playlist-backend/
│
├── pom.xml
│
├── proto-contract/
│   ├── pom.xml
│   └── src/main/proto/
│       ├── common.proto
│       ├── video.proto
│       └── playlist.proto
│
├── common-lib/
│   ├── pom.xml
│   └── src/main/java/com/example/youtube/common/
│
├── video-service/
│   ├── pom.xml
│   └── src/main/java/com/example/youtube/video/
│
├── playlist-service/
│   ├── pom.xml
│   └── src/main/java/com/example/youtube/playlist/
│
├── aggregation-worker/
│   ├── pom.xml
│   └── src/main/java/com/example/youtube/aggregation/
│
├── docker/
│   ├── video-service.Dockerfile
│   ├── playlist-service.Dockerfile
│   └── aggregation-worker.Dockerfile
│
├── k8s/
│   ├── namespace.yaml
│   ├── postgres/
│   ├── video-service/
│   ├── playlist-service/
│   └── aggregation-worker/
│
└── README.md
```

Required deployable modules:

```text
video-service
playlist-service
aggregation-worker
```

Non-deployable shared modules:

```text
proto-contract
common-lib
```

---

## 6. Module Responsibilities

## 6.1 proto-contract

This module stores all protobuf definitions used by the project.

Responsibilities:

```text
- Store shared protobuf files.
- Generate Java protobuf models.
- Generate Java gRPC stubs.
- Provide reusable contracts for internal service communication.
```

Rules:

```text
- Reuse existing protobuf files if they already exist.
- Do not delete existing messages, fields, or RPC definitions.
- Do not rename existing fields.
- Do not reuse old field numbers.
- Only add new messages, fields, or RPCs when needed.
- Keep protobuf backward-compatible.
- Use reserved field numbers if removing fields is unavoidable.
```

Example files:

```text
common.proto
video.proto
playlist.proto
```

Suggested `common.proto`:

```proto
syntax = "proto3";

package youtube.common;

option java_multiple_files = true;
option java_package = "com.example.youtube.proto.common";
option java_outer_classname = "CommonProto";

message EmptyRequest {}

message CommonStatus {
  bool success = 1;
  string message = 2;
  string error_code = 3;
}

message PageRequest {
  int32 page = 1;
  int32 size = 2;
}

message PageResponse {
  int32 page = 1;
  int32 size = 2;
  int64 total_elements = 3;
  int32 total_pages = 4;
}
```

Suggested `video.proto`:

```proto
syntax = "proto3";

package youtube.video;

option java_multiple_files = true;
option java_package = "com.example.youtube.proto.video";
option java_outer_classname = "VideoProto";

import "common.proto";

message VideoInfo {
  int64 id = 1;
  string title = 2;
  string description = 3;
  string thumbnail_url = 4;
  string video_url = 5;
  int32 duration_sec = 6;
  int64 view_count = 7;
  int64 like_count = 8;
  string status = 9;
  int64 uploader_id = 10;
  int64 category_id = 11;
}

message GetVideoByIdRequest {
  int64 video_id = 1;
}

message GetVideoByIdResponse {
  VideoInfo video = 1;
}

message GetVideosByIdsRequest {
  repeated int64 video_ids = 1;
}

message GetVideosByIdsResponse {
  repeated VideoInfo videos = 1;
}

message TrendingCandidate {
  int64 video_id = 1;
  int64 views_24h = 2;
  int64 likes_24h = 3;
  double average_watch_seconds = 4;
  double score = 5;
}

message GetTrendingCandidatesRequest {
  int32 limit = 1;
  int32 window_hours = 2;
}

message GetTrendingCandidatesResponse {
  repeated TrendingCandidate candidates = 1;
}

service VideoQueryService {
  rpc GetVideoById(GetVideoByIdRequest) returns (GetVideoByIdResponse);
  rpc GetVideosByIds(GetVideosByIdsRequest) returns (GetVideosByIdsResponse);
  rpc GetTrendingCandidates(GetTrendingCandidatesRequest) returns (GetTrendingCandidatesResponse);
}
```

Suggested `playlist.proto`:

```proto
syntax = "proto3";

package youtube.playlist;

option java_multiple_files = true;
option java_package = "com.example.youtube.proto.playlist";
option java_outer_classname = "PlaylistProto";

import "common.proto";

message PlaylistItemInfo {
  int64 playlist_item_id = 1;
  int64 playlist_id = 2;
  int64 video_id = 3;
  int32 position = 4;
  string source = 5;
  string added_at = 6;
}

message PlaylistInfo {
  int64 id = 1;
  string title = 2;
  string description = 3;
  string owner_type = 4;
  int64 owner_id = 5;
  string playlist_type = 6;
  string visibility = 7;
  bool editable = 8;
  repeated PlaylistItemInfo items = 9;
}

message EnsureDefaultUserPlaylistsRequest {
  int64 user_id = 1;
}

message EnsureDefaultUserPlaylistsResponse {
  youtube.common.CommonStatus status = 1;
}

message AddHistoryItemRequest {
  int64 user_id = 1;
  int64 video_id = 2;
  string watched_at = 3;
  int32 watch_duration_sec = 4;
}

message AddHistoryItemResponse {
  youtube.common.CommonStatus status = 1;
}

message UpdateSystemPlaylistRequest {
  string playlist_code = 1;
  repeated int64 video_ids = 2;
}

message UpdateSystemPlaylistResponse {
  youtube.common.CommonStatus status = 1;
}

service PlaylistInternalService {
  rpc EnsureDefaultUserPlaylists(EnsureDefaultUserPlaylistsRequest)
      returns (EnsureDefaultUserPlaylistsResponse);

  rpc AddHistoryItem(AddHistoryItemRequest)
      returns (AddHistoryItemResponse);

  rpc UpdateSystemPlaylist(UpdateSystemPlaylistRequest)
      returns (UpdateSystemPlaylistResponse);
}
```

---

## 6.2 common-lib

This module stores shared Java code.

Allowed contents:

```text
- Common exceptions
- Error response model
- API response wrapper
- Header constants
- Request context utilities
- Common enums
- Common validation helpers
- Date/time utilities
- gRPC client configuration helpers
```

Do not put business logic here.

Recommended package structure:

```text
com.example.youtube.common
├── context
│   ├── RequestContext.java
│   └── RequestContextHolder.java
├── exception
│   ├── BusinessException.java
│   ├── UnauthorizedException.java
│   ├── ForbiddenException.java
│   └── NotFoundException.java
├── response
│   ├── ApiResponse.java
│   └── ErrorCode.java
├── header
│   └── HeaderConstants.java
├── grpc
│   └── GrpcClientProperties.java
└── util
```

Example `HeaderConstants`:

```java
public final class HeaderConstants {
    public static final String USER_ID = "X-User-Id";
    public static final String USER_ROLE = "X-User-Role";
    public static final String REQUEST_ID = "X-Request-Id";

    private HeaderConstants() {
    }
}
```

Example `ApiResponse`:

```java
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String errorCode;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("OK")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}
```

---

## 6.3 video-service

The `video-service` owns video metadata and video statistics.

Responsibilities:

```text
- Create video metadata.
- Update video metadata.
- Get video detail.
- Get videos by list of IDs.
- Search/filter videos.
- Store video view events.
- Increase view count.
- Provide trending candidates to aggregation-worker.
- Expose REST APIs for Gateway.
- Expose gRPC APIs for internal services.
```

Package structure:

```text
com.example.youtube.video
├── VideoServiceApplication.java
├── controller
├── service
├── repository
├── entity
├── dto
│   ├── request
│   └── response
├── mapper
├── grpc
│   └── server
├── config
└── exception
```

REST APIs:

```http
POST   /api/videos
GET    /api/videos/{videoId}
GET    /api/videos?ids=1,2,3
GET    /api/videos/search
POST   /api/videos/{videoId}/views
PUT    /api/videos/{videoId}
DELETE /api/videos/{videoId}
```

gRPC APIs:

```text
VideoQueryService.GetVideoById
VideoQueryService.GetVideosByIds
VideoQueryService.GetTrendingCandidates
```

Main database tables:

```text
videos
video_view_events
video_categories
```

Suggested table: `videos`

```sql
CREATE TABLE videos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    video_url TEXT,
    thumbnail_url TEXT,
    duration_sec INT,
    view_count BIGINT DEFAULT 0,
    like_count BIGINT DEFAULT 0,
    status VARCHAR(30) NOT NULL,
    category_id BIGINT,
    uploader_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

Suggested table: `video_view_events`

```sql
CREATE TABLE video_view_events (
    id BIGSERIAL PRIMARY KEY,
    video_id BIGINT NOT NULL,
    user_id BIGINT,
    viewed_at TIMESTAMP NOT NULL,
    watch_duration_sec INT,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_video_view_events_video_time
ON video_view_events(video_id, viewed_at);

CREATE INDEX idx_video_view_events_time
ON video_view_events(viewed_at);
```

Important rules:

```text
- playlist-service must not query video-service database directly.
- playlist-service must call video-service through gRPC when it needs video details.
- video-service must not query playlist-service database.
```

---

## 6.4 playlist-service

The `playlist-service` is the core business service of this project.

Responsibilities:

```text
- Create user playlists.
- Update user playlists.
- Delete user playlists.
- Add videos to playlist.
- Remove videos from playlist.
- Reorder playlist items.
- Manage Watch Later playlist.
- Manage History playlist.
- Manage System playlists.
- Check playlist ownership.
- Check playlist visibility.
- Return playlist detail together with video metadata.
- Expose REST APIs for Gateway.
- Expose gRPC APIs for aggregation-worker.
```

Package structure:

```text
com.example.youtube.playlist
├── PlaylistServiceApplication.java
├── controller
├── service
├── repository
├── entity
├── dto
│   ├── request
│   └── response
├── mapper
├── grpc
│   ├── server
│   └── client
├── config
└── exception
```

REST APIs:

```http
POST   /api/playlists
GET    /api/playlists/{playlistId}
GET    /api/playlists/me
PUT    /api/playlists/{playlistId}
DELETE /api/playlists/{playlistId}

POST   /api/playlists/{playlistId}/items
DELETE /api/playlists/{playlistId}/items/{videoId}
PATCH  /api/playlists/{playlistId}/items/reorder

GET    /api/system-playlists
GET    /api/system-playlists/{code}

GET    /api/watch-later
POST   /api/watch-later/{videoId}
DELETE /api/watch-later/{videoId}

GET    /api/history
DELETE /api/history
```

gRPC APIs:

```text
PlaylistInternalService.EnsureDefaultUserPlaylists
PlaylistInternalService.AddHistoryItem
PlaylistInternalService.UpdateSystemPlaylist
```

Main database tables:

```text
playlists
playlist_items
```

Suggested table: `playlists`

```sql
CREATE TABLE playlists (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,

    owner_type VARCHAR(30) NOT NULL,
    owner_id BIGINT,

    playlist_type VARCHAR(50) NOT NULL,
    playlist_code VARCHAR(100),

    visibility VARCHAR(30) NOT NULL,
    editable BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_playlists_owner
ON playlists(owner_type, owner_id);

CREATE INDEX idx_playlists_type_code
ON playlists(playlist_type, playlist_code);
```

Suggested table: `playlist_items`

```sql
CREATE TABLE playlist_items (
    id BIGSERIAL PRIMARY KEY,
    playlist_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,

    position INT NOT NULL,
    source VARCHAR(30) NOT NULL,

    added_by BIGINT,
    added_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_playlist_items_playlist
        FOREIGN KEY (playlist_id)
        REFERENCES playlists(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_playlist_video
        UNIQUE (playlist_id, video_id)
);

CREATE INDEX idx_playlist_items_playlist_position
ON playlist_items(playlist_id, position);

CREATE INDEX idx_playlist_items_video_id
ON playlist_items(video_id);
```

Playlist ownership model:

```text
owner_type:
- USER
- SYSTEM

owner_id:
- user ID for user-owned playlist
- null for system playlist

playlist_type:
- USER_CREATED
- WATCH_LATER
- HISTORY
- SYSTEM_TRENDING
- SYSTEM_GENRE
- SYSTEM_WEEKLY_HOT

visibility:
- PUBLIC
- PRIVATE
- UNLISTED
```

Important rules:

```text
- Do not create separate tables for user playlists and system playlists.
- Use the same playlists table and playlist_items table.
- Differentiate behavior by owner_type, owner_id, playlist_type, playlist_code, visibility, and editable.
```

---

## 6.5 aggregation-worker

The `aggregation-worker` is responsible for background jobs and system playlist updates.

Responsibilities:

```text
- Generate Trending playlist periodically.
- Generate genre-based playlists periodically.
- Generate weekly hot playlist periodically.
- Receive or simulate VIDEO_WATCHED events.
- Update History playlist through playlist-service gRPC.
- Update System playlist through playlist-service gRPC.
- Call video-service through gRPC to obtain candidate videos.
```

Package structure:

```text
com.example.youtube.aggregation
├── AggregationWorkerApplication.java
├── job
├── service
├── grpc
│   └── client
├── config
└── scheduler
```

Example jobs:

```text
TrendingAggregationJob
GenreAggregationJob
WeeklyHotAggregationJob
HistoryUpdateJob
```

Correct flow for trending:

```text
aggregation-worker
    -> gRPC video-service.GetTrendingCandidates
    -> compute final ranking
    -> gRPC playlist-service.UpdateSystemPlaylist
```

Correct flow for history:

```text
video watched event
    -> aggregation-worker receives event
    -> gRPC playlist-service.AddHistoryItem
```

Incorrect flow:

```text
aggregation-worker
    -> directly query video-service database
    -> directly update playlist-service database
```

This is forbidden.

For demo, `aggregation-worker` may use `@Scheduled`.

Example:

```java
@Scheduled(fixedDelayString = "${app.jobs.trending.fixed-delay-ms:600000}")
public void updateTrendingPlaylist() {
    // call video-service through gRPC
    // compute top videos
    // call playlist-service through gRPC
}
```

---

## 7. Business Rules

## 7.1 User-created Playlist

Rules:

```text
- owner_type = USER
- owner_id = current user's ID
- playlist_type = USER_CREATED
- editable = true
- visibility can be PUBLIC, PRIVATE, or UNLISTED
- owner can add videos.
- owner can remove videos.
- owner can reorder videos.
- owner can update title and description.
- owner can delete the playlist.
```

---

## 7.2 Watch Later Playlist

Rules:

```text
- owner_type = USER
- owner_id = current user's ID
- playlist_type = WATCH_LATER
- visibility = PRIVATE
- editable = true
- automatically created when needed
- user can add videos
- user can remove videos
- user can reorder videos if implemented
```

There must be only one Watch Later playlist per user.

Suggested unique constraint can be handled at application level or database level:

```text
owner_type = USER
owner_id = user_id
playlist_type = WATCH_LATER
```

---

## 7.3 History Playlist

Rules:

```text
- owner_type = USER
- owner_id = current user's ID
- playlist_type = HISTORY
- visibility = PRIVATE
- editable = false or limited_editable
- automatically created when needed
- updated mainly by system event or aggregation-worker
- user may clear history
```

There must be only one History playlist per user.

When the same video is watched again:

```text
Option 1: Move existing item to the top and update added_at.
Option 2: Keep unique item and update timestamp.
Option 3: Allow duplicate history records in separate history table.
```

For this project, prefer:

```text
Keep unique video in History playlist.
If video already exists, update added_at and move it to position 1.
```

---

## 7.4 System Playlist

Rules:

```text
- owner_type = SYSTEM
- owner_id = null
- playlist_type = SYSTEM_TRENDING / SYSTEM_GENRE / SYSTEM_WEEKLY_HOT
- editable = false from REST user APIs
- visibility = PUBLIC
- user can only read
- aggregation-worker can update through internal gRPC
```

Examples:

```text
SYSTEM_TRENDING + playlist_code = TRENDING
SYSTEM_GENRE + playlist_code = GENRE_POP
SYSTEM_WEEKLY_HOT + playlist_code = WEEKLY_HOT
```

---

## 8. Playlist Item Position Rules

Each video item in a playlist must have a position.

Rules:

```text
- Position starts from 1.
- New video should be appended to the end by default.
- When removing video, compact positions.
- When reordering, validate that all item IDs belong to the same playlist.
- Prevent duplicate video in the same playlist.
```

Recommended unique constraint:

```sql
UNIQUE (playlist_id, video_id)
```

Recommended index:

```sql
CREATE INDEX idx_playlist_items_playlist_position
ON playlist_items(playlist_id, position);
```

Reorder request example:

```json
{
  "items": [
    {
      "playlistItemId": 10,
      "position": 1
    },
    {
      "playlistItemId": 15,
      "position": 2
    }
  ]
}
```

---

## 9. Trending Calculation Rule

Do not calculate trending only from total `view_count`.

Reason:

```text
If trending is based only on total view_count, old videos with high historical views will always dominate.
```

Use recent activity instead.

Minimum demo formula:

```text
score = views_in_24h * 1.0
      + likes_in_24h * 2.0
      + average_watch_seconds * 0.1
```

Minimum implementation:

```text
- Query video_view_events in the last 24 hours.
- Count views per video.
- Sort by score descending.
- Pick top 50.
- Update SYSTEM_TRENDING playlist.
```

Example SQL:

```sql
SELECT
    video_id,
    COUNT(*) AS views_24h,
    AVG(watch_duration_sec) AS avg_watch_seconds
FROM video_view_events
WHERE viewed_at >= NOW() - INTERVAL '24 hours'
GROUP BY video_id
ORDER BY views_24h DESC
LIMIT 50;
```

---

## 10. Inter-service Communication Rules

Use REST only for Gateway-to-service APIs.

Use gRPC for internal service-to-service communication.

Expected communication:

```text
Gateway
    -> REST
    -> video-service

Gateway
    -> REST
    -> playlist-service

playlist-service
    -> gRPC
    -> video-service

aggregation-worker
    -> gRPC
    -> video-service

aggregation-worker
    -> gRPC
    -> playlist-service
```

Forbidden communication:

```text
playlist-service
    -> direct SQL query
    -> video_db

aggregation-worker
    -> direct SQL query
    -> video_db or playlist_db

video-service
    -> direct SQL query
    -> playlist_db
```

---

## 11. Database Ownership Rule

Use database-per-service style.

Recommended:

```text
video-service owns video_db.
playlist-service owns playlist_db.
aggregation-worker owns no business database.
```

For Minikube/local demo, it is acceptable to use one PostgreSQL instance with multiple databases or schemas:

```text
video_db
playlist_db
```

Rules:

```text
- A service can only access its own database.
- Cross-service data must be fetched through gRPC.
- Do not create foreign keys across service databases.
```

---

## 12. Entity and DTO Rules

Use JPA entities inside each service.

Use Lombok for boilerplate.

Example entity style:

```java
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")
public class PlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private PlaylistType playlistType;

    private String playlistCode;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private Boolean editable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
```

Rules:

```text
- Do not expose JPA entities directly from REST controllers.
- REST controllers must return DTO/Response objects.
- Use mapper classes for conversion.
```

---

## 13. MapStruct Rules

Use MapStruct for DTO mapping.

Example:

```java
@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    PlaylistResponse toResponse(PlaylistEntity entity);

    PlaylistEntity toEntity(CreatePlaylistRequest request);
}
```

Rules:

```text
- Use MapStruct for Entity <-> DTO conversion.
- Use separate mapper for Protobuf <-> Domain conversion if needed.
- Avoid manual mapping unless the logic is complex.
```

---

## 14. REST API Response Rule

All REST APIs should return consistent response format.

Success response:

```json
{
  "success": true,
  "message": "OK",
  "errorCode": null,
  "data": {}
}
```

Error response:

```json
{
  "success": false,
  "message": "Playlist not found",
  "errorCode": "PLAYLIST_NOT_FOUND",
  "data": null
}
```

Use correct HTTP status codes:

```text
200 OK
201 Created
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
500 Internal Server Error
```

---

## 15. Error Handling Rules

Each service must have a global exception handler.

Use:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
}
```

Common exceptions:

```text
BusinessException
UnauthorizedException
ForbiddenException
NotFoundException
ConflictException
ValidationException
```

Business-specific exceptions:

```text
VideoNotFoundException
PlaylistNotFoundException
DuplicatePlaylistItemException
InvalidPlaylistTypeException
PlaylistPermissionDeniedException
SystemPlaylistReadOnlyException
```

---

## 16. Logging Rules

Use structured logs where possible.

Log important events:

```text
- Create video
- Update video
- Record view event
- Create playlist
- Add video to playlist
- Remove video from playlist
- Reorder playlist
- Clear history
- Update system playlist
- Generate trending playlist
- gRPC call success/failure
```

Do not log:

```text
- Tokens
- Passwords
- Sensitive authorization headers
```

Include `X-Request-Id` in logs if available.

---

## 17. Configuration Rules

Use environment variables in deployment.

Do not hardcode localhost in Kubernetes configuration.

Example Spring properties:

```yaml
server:
  port: 8080

grpc:
  server:
    port: 9090

spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

app:
  grpc:
    video-service:
      host: ${VIDEO_GRPC_HOST:video-service}
      port: ${VIDEO_GRPC_PORT:9090}
    playlist-service:
      host: ${PLAYLIST_GRPC_HOST:playlist-service}
      port: ${PLAYLIST_GRPC_PORT:9090}
```

For local development, service host can be localhost.

For Kubernetes/Minikube:

```text
video-service
playlist-service
```

Use Kubernetes service names, not localhost.

---

## 18. Docker Rules

Each deployable module must have its own Dockerfile.

Required images:

```text
youtube/video-service:local
youtube/playlist-service:local
youtube/aggregation-worker:local
```

Non-deployable modules do not need Docker images:

```text
proto-contract
common-lib
```

Example Dockerfile pattern:

```dockerfile
FROM eclipse-temurin:17-jre

WORKDIR /app

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build example:

```bash
docker build \
  -f docker/video-service.Dockerfile \
  --build-arg JAR_FILE=video-service/target/video-service.jar \
  -t youtube/video-service:local .
```

---

## 19. Minikube Deployment Rules

Create Kubernetes manifests for each deployable service.

Required resource types:

```text
Namespace
Deployment
Service
ConfigMap
Secret
```

For PostgreSQL demo:

```text
Deployment or StatefulSet
PersistentVolumeClaim
Service
Secret
```

Recommended namespace:

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: youtube-demo
```

Service names:

```text
video-service
playlist-service
aggregation-worker
postgres
```

Internal DNS:

```text
video-service.youtube-demo.svc.cluster.local
playlist-service.youtube-demo.svc.cluster.local
postgres.youtube-demo.svc.cluster.local
```

Example service environment variables:

```yaml
env:
  - name: DB_URL
    value: jdbc:postgresql://postgres:5432/video_db
  - name: DB_USERNAME
    valueFrom:
      secretKeyRef:
        name: postgres-secret
        key: username
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: postgres-secret
        key: password
  - name: VIDEO_GRPC_HOST
    value: video-service
  - name: VIDEO_GRPC_PORT
    value: "9090"
```

---

## 20. Local Development Commands

Build all modules:

```bash
mvn clean install
```

Build without tests:

```bash
mvn clean install -DskipTests
```

Run video-service locally:

```bash
mvn -pl video-service spring-boot:run
```

Run playlist-service locally:

```bash
mvn -pl playlist-service spring-boot:run
```

Run aggregation-worker locally:

```bash
mvn -pl aggregation-worker spring-boot:run
```

---

## 21. Minikube Commands

Start Minikube:

```bash
minikube start
```

Use Minikube Docker daemon:

```bash
eval $(minikube docker-env)
```

On Windows PowerShell:

```powershell
minikube docker-env | Invoke-Expression
```

Build images:

```bash
docker build -f docker/video-service.Dockerfile -t youtube/video-service:local .
docker build -f docker/playlist-service.Dockerfile -t youtube/playlist-service:local .
docker build -f docker/aggregation-worker.Dockerfile -t youtube/aggregation-worker:local .
```

Deploy:

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/video-service/
kubectl apply -f k8s/playlist-service/
kubectl apply -f k8s/aggregation-worker/
```

Check pods:

```bash
kubectl get pods -n youtube-demo
```

Check services:

```bash
kubectl get svc -n youtube-demo
```

Check logs:

```bash
kubectl logs -f deployment/video-service -n youtube-demo
kubectl logs -f deployment/playlist-service -n youtube-demo
kubectl logs -f deployment/aggregation-worker -n youtube-demo
```

Port forward:

```bash
kubectl port-forward svc/video-service 8081:8080 -n youtube-demo
kubectl port-forward svc/playlist-service 8082:8080 -n youtube-demo
```

---

## 22. Testing Rules

Minimum tests:

```text
- Unit test for playlist creation.
- Unit test for playlist ownership validation.
- Unit test for adding item to playlist.
- Unit test for preventing duplicate playlist item.
- Unit test for removing item and compacting positions.
- Unit test for reordering playlist items.
- Unit test for system playlist read-only rule.
- Integration test for repository layer.
- gRPC server/client tests if possible.
```

Important test cases:

```text
- User can create playlist.
- User can update own playlist.
- User cannot update another user's private playlist.
- User can view public playlist.
- User cannot edit system playlist through REST.
- Aggregation worker can update system playlist through gRPC.
- Watch Later playlist is auto-created.
- History playlist is auto-created.
- History is updated by internal gRPC call.
- Trending playlist is updated by aggregation-worker.
```

---

## 23. README Requirements

The root README.md should include:

```text
- Project overview
- Architecture diagram
- Module description
- Tech stack
- Local run guide
- Docker build guide
- Minikube deployment guide
- REST API examples
- gRPC overview
- Database ownership explanation
- Important business rules
```

README should explain clearly that:

```text
- Gateway handles authentication.
- Business services trust X-User-Id and X-User-Role headers.
- Internal communication uses gRPC.
- Each service owns its own database.
```

---

## 24. Agent Implementation Priority

When generating or modifying code, follow this priority order:

```text
1. Create or update Maven multi-module structure.
2. Configure proto-contract and protobuf generation.
3. Create or update common-lib.
4. Implement video-service.
5. Implement playlist-service.
6. Implement aggregation-worker.
7. Add REST APIs.
8. Add gRPC APIs.
9. Add database entities and repositories.
10. Add MapStruct mappers.
11. Add Dockerfiles.
12. Add Kubernetes manifests for Minikube.
13. Add sample README.
14. Add tests.
```

Do not start with Kubernetes before services can run locally.

---

## 25. Coding Style Rules

General rules:

```text
- Use clear package structure.
- Keep controller thin.
- Put business logic in service layer.
- Put persistence logic in repository layer.
- Put mapping logic in mapper layer.
- Use DTOs for REST request and response.
- Use protobuf messages for gRPC request and response.
- Use constructor injection.
- Avoid field injection.
- Use meaningful exception classes.
- Use validation annotations for request DTOs.
```

Preferred dependency injection:

```java
@RequiredArgsConstructor
@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;
    private final VideoGrpcClient videoGrpcClient;
}
```

Avoid:

```java
@Autowired
private PlaylistRepository playlistRepository;
```

---

## 26. Important Constraints

The agent must obey these constraints:

```text
- Do not implement authentication logic.
- Do not create AuthService.
- Do not create login/register APIs.
- Do not query another service's database.
- Do not expose JPA entities from REST APIs.
- Do not remove existing protobuf contracts.
- Do not break protobuf backward compatibility.
- Do not create one big monolithic service.
- Do not put all business logic into Gateway.
- Do not use hardcoded localhost in Kubernetes manifests.
- Do not ignore gRPC for internal service calls.
- Do not create separate tables for user playlist and system playlist.
- Do not let users edit system playlists through REST.
```

---

## 27. Expected Final Deliverables

The final project should contain:

```text
- Maven multi-module Spring Boot project
- proto-contract module
- common-lib module
- video-service module
- playlist-service module
- aggregation-worker module
- REST APIs for external Gateway
- gRPC APIs for internal communication
- Lombok usage
- MapStruct usage
- PostgreSQL database configuration
- Dockerfile for each deployable module
- Kubernetes manifests for Minikube
- README with local and Minikube instructions
- Unit/integration tests for important business rules
```

---

## 28. Short Prompt for Coding Agent

Use this short prompt when giving direct instructions to an AI coding agent:

```text
Build a Java Spring Boot 3.x Maven multi-module microservice backend for a YouTube-like playlist system.

Use existing protobuf contracts when available. Extend protobuf only when required and keep backward compatibility. Internal service communication must use gRPC. REST APIs are exposed for an external Gateway. Authentication is handled by the Gateway, so do not implement login/register/JWT logic. Business services should read trusted headers such as X-User-Id and X-User-Role.

Required modules:
- proto-contract
- common-lib
- video-service
- playlist-service
- aggregation-worker

video-service manages video metadata, video view events, and exposes gRPC APIs for video lookup and trending candidates.

playlist-service manages user-created playlists, Watch Later, History, and System playlists. Use one playlist model with owner_type, owner_id, playlist_type, playlist_code, visibility, editable, and playlist_items. User-created and system-generated playlists must not use separate tables.

aggregation-worker runs scheduled jobs to generate system playlists such as Trending. It must call video-service and playlist-service through gRPC, not direct database access.

Use Lombok, MapStruct, Spring Data JPA, PostgreSQL, Docker, and Kubernetes manifests for Minikube. Each deployable module must have its own Dockerfile and Kubernetes Deployment/Service.

Do not create AuthService. Do not query another service’s database. Do not expose JPA entities directly. Do not hardcode localhost in Kubernetes configs.
```

---

## 29. Recommended First Milestone

The first milestone should deliver a runnable local demo:

```text
- Maven multi-module project builds successfully.
- video-service starts.
- playlist-service starts.
- aggregation-worker starts.
- video-service can create and return video metadata.
- playlist-service can create playlist and add video.
- playlist-service calls video-service through gRPC to enrich playlist response.
- aggregation-worker calls video-service and playlist-service through gRPC to update Trending playlist.
```

After milestone 1, implement Docker and Minikube deployment.

---

## 30. Final Design Principle

The most important design principle:

```text
Playlist is a shared domain concept.
User-created playlist, Watch Later, History, and System playlist should use the same playlists and playlist_items tables.

The difference is not in table structure.
The difference is in owner_type, owner_id, playlist_type, playlist_code, visibility, editable policy, and update source.
```

System design summary:

```text
Gateway handles authentication.
Business services handle domain logic.
gRPC handles internal service communication.
Each service owns its own database.
Aggregation worker updates system-generated data.
Docker packages each service separately.
Minikube runs the full demo environment.
```
