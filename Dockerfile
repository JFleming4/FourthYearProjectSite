FROM openjdk:8-jre
VOLUME ["/app", "/tmp"]
WORKDIR /app
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
COPY docker/wait-for-it.sh wait-for-it.sh
COPY docker/start.sh start.sh
RUN sh -c 'touch app.jar'
ENTRYPOINT ["./start.sh"]
