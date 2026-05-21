# YouTube Playlist Backend (Spring Boot + gRPC)

## Overview
This project is a Java 17 Spring Boot multi-module backend for a YouTube-like playlist platform.

External Gateway handles authentication and forwards trusted headers:
- `X-User-Id`
- `X-User-Role`
- `X-Request-Id`

Business services trust these headers and enforce domain authorization.

## Modules
- `proto-contract`: protobuf contracts and generated gRPC stubs.
- `common-lib`: shared exceptions, API response, header/context utilities.
- `video-service`: video metadata + view events + trending candidates.
- `playlist-service`: user playlists, watch later, history, system playlists.
- `aggregation-worker`: scheduled jobs updating system playlists via gRPC.

## Architecture
- Gateway -> services: REST
- Internal services -> services: gRPC
- Worker -> services: gRPC
- No auth/login/JWT implementation inside business services

## Tech Stack
- Java 17
- Spring Boot 3.5.x
- Spring Web, Spring Data JPA, Spring Validation
- PostgreSQL
- gRPC + Protocol Buffers
- Lombok, MapStruct
- Docker
- Kubernetes/Minikube

## Run Local
Build without tests:

```bash
mvn clean install -DskipTests
```

Run modules:

```bash
mvn -pl video-service spring-boot:run
mvn -pl playlist-service spring-boot:run
mvn -pl aggregation-worker spring-boot:run
```

Default ports:
- `video-service`: HTTP `8081`, gRPC `9091`
- `playlist-service`: HTTP `8082`, gRPC `9092`
- `aggregation-worker`: HTTP `8083`

## Docker
```bash
docker build -f docker/video-service.Dockerfile -t youtube/video-service:local .
docker build -f docker/playlist-service.Dockerfile -t youtube/playlist-service:local .
docker build -f docker/aggregation-worker.Dockerfile -t youtube/aggregation-worker:local .
```

## Minikube
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/video-service/
kubectl apply -f k8s/playlist-service/
kubectl apply -f k8s/aggregation-worker/
```

## Main REST APIs
Video:
- `POST /api/videos`
- `GET /api/videos/{videoId}`
- `GET /api/videos?ids=1,2,3`
- `GET /api/videos/search`
- `POST /api/videos/{videoId}/views`
- `PUT /api/videos/{videoId}`
- `DELETE /api/videos/{videoId}`

Playlist:
- `POST /api/playlists`
- `GET /api/playlists/{playlistId}`
- `GET /api/playlists/me`
- `PUT /api/playlists/{playlistId}`
- `DELETE /api/playlists/{playlistId}`
- `POST /api/playlists/{playlistId}/items`
- `DELETE /api/playlists/{playlistId}/items/{videoId}`
- `PATCH /api/playlists/{playlistId}/items/reorder`
- `GET /api/system-playlists`
- `GET /api/system-playlists/{code}`
- `GET /api/watch-later`
- `POST /api/watch-later/{videoId}`
- `DELETE /api/watch-later/{videoId}`
- `GET /api/history`
- `DELETE /api/history`

## Internal gRPC
Video:
- `VideoQueryService.GetVideoById`
- `VideoQueryService.GetVideosByIds`
- `VideoQueryService.GetTrendingCandidates`

Playlist:
- `PlaylistInternalService.EnsureDefaultUserPlaylists`
- `PlaylistInternalService.AddHistoryItem`
- `PlaylistInternalService.UpdateSystemPlaylist`
