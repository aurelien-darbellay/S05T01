FROM openjdk:21
COPY build/libs/blackjack-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]