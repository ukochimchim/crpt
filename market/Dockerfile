FROM java:8
EXPOSE 8080

COPY target/*.jar /usr/app/app.jar
WORKDIR /usr/app
ENTRYPOINT exec java -jar app.jar