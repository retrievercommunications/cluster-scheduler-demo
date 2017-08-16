FROM anapsix/alpine-java:8
LABEL maintainer="callum.robertson@retrievercommunications.com.au"
COPY build/libs/hello-server.jar hello-server.jar
COPY config.yml config.yml
EXPOSE 8080 8081
ENTRYPOINT ["java", "-jar", "hello-server.jar", "server", "config.yml"]