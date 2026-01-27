#Stage  1 :  build jar
FROM gradle:8.14-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -PexcludeTags=db

#Stage  2 :  build image
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder app/build/libs/blackjack-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
