FROM openjdk:21
LABEL authors="CDA-10"

EXPOSE 8082

WORKDIR /app

COPY harmonia_api_nico.jar .

CMD ["java", "-jar", "harmonia_api_nico.jar"]
