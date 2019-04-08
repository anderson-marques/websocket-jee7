# Simple Websockets JEE7 Application

This application runs on Payara Server.

## Requirements

- Docker Compose

## To Test

`docker-compose build tests && docker-compose run tests`

## To run local

`docker-compose build app && docker-compose run app`

The REST application is available on:

```bash
http://localhost:8080/websockets-jee7
```

To see the active rooms:

```bash
http://localhost:8080/websockets-jee7/api/rooms
```

The Websocket endpoint is

```url
ws://localhost:8080/websockets-jee7/chats/{chatId}/{userId}
```

Example: `conn = new WebSocket("ws://localhost:8080/websockets-jee7/chat/3/3")`