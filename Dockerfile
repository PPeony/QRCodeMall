FROM java:8

COPY *.jar /app.jar

#CMD ["--server.port=8081"]

EXPOSE 8090

ENTRYPOINT ["java","-jar","/app.jar"]