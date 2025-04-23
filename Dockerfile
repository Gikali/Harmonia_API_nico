FROM openjdk:21
LABEL authors="CDA-10"

EXPOSE 8082

WORKDIR /app

COPY package.jar .

CMD ["java", "-jar", "package.jar"]
