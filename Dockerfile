FROM java:8

COPY *.jar /app.jar

ENV TZ=Asia/Shanghai
# 设置时区，保证和数据库的时区一致

EXPOSE 8090

ENTRYPOINT ["java","-jar","/app.jar"]