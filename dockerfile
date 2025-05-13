FROM openjdk:21
LABEL maintainer="S05T01"
ADD build/libs/S05T01-0.0.1.jar S05T01-0.0.1.jar
ENTRYPOINT ["java","-jar","S05T01-0.0.1.jar"]