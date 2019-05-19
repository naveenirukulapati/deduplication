FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/deduplication-0.0.1-SNAPSHOT.jar deduplication-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar","/deduplication-0.0.1-SNAPSHOT.jar"]
EXPOSE 8888