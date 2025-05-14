# 🃏 Reactive Blackjack API

This project is a **Spring Boot Reactive application** that allows players to play Blackjack online. It uses **MongoDB**
to store game data and **MySQL** (via R2DBC) to persist player information and rankings.

## 🚀 Features

- Reactive programming using **Spring WebFlux**
- Game persistence in **MongoDB**
- Player rankings and stats in **MySQL** using **R2DBC**
- Interactive API documentation via **Swagger UI**
- Designed for containerized deployment via **Docker** and **Docker Compose**

---

## 🧱 Technologies & Dependencies

- Spring Boot (Reactive stack)
- Spring WebFlux
- Spring Data MongoDB Reactive
- Spring Data R2DBC (with MySQL)
- JUnit 5 / Reactor Test
- Java 21
- Gradle 8.14

## 📚 API Endpoints

🎮 /game — Game Controller

| Method | Endpoint            | Description                        |
|--------|---------------------|------------------------------------|
| GET    | `/game`             | Get all games                      |
| GET    | `/game/{id}`        | Get a game by ID                   |
| POST   | `/game/new`         | Create a new game for a player     |
| POST   | `/game/{id}/bet`    | Start a new turn with a bet        |
| POST   | `/game/{id}/play`   | Play a turn (`hit`, `stand`, etc.) |
| POST   | `/game/{id}/save`   | Save the current game state        |
| DELETE | `/game/{id}/delete` | Delete a game by ID                |

🧑 / — Player Controller

| Method | Endpoint             | Description                      |
|--------|----------------------|----------------------------------|
| PUT    | `/player/{username}` | Update player name details       |
| GET    | `/ranking`           | Get all players ranked by points |

## 📖 API Documentation

Swagger UI is available at:
http://localhost:8080/swagger-ui/index.html

## 🔗 GitHub

You can find the source code for this project on GitHub:
https://github.com/aurelien-darbellay/S05T01.git

## 🔗 DockerHub repository

https://hub.docker.com/repository/docker/aureliendarbellay/s05t01/general

## 🐳 Run with docker

1. git clone https://github.com/aurelien-darbellay/S05T01.git
2. docker compose up (or docker compose up -d to run the process in the background)
