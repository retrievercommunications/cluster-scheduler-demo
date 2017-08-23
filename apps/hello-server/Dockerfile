FROM anapsix/alpine-java:8
LABEL maintainer="callum.robertson@retrievercommunications.com.au"
COPY build/libs/hello-server-all.jar hello-server-all.jar
COPY config.yml config.yml
EXPOSE 8080 8081
ENTRYPOINT ["java", "-jar", "hello-server-all.jar", "server", "config.yml"]