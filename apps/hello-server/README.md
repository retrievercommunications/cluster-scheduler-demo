# hello-server

The hello-server is a simple Dropwizard application that can be used for demo purposes.

## Getting started

Build the project using Gradle:

	./gradlew build

Run the server locally:

	java -jar build/libs/hello-server.jar server config.yml

Send a GET request to confirm that it's running:

	curl -X GET http://127.0.0.1:8080/hello

View the healthcheck results:

	curl -X GET http://127.0.0.1:8081/healthcheck

## Running through Docker

Create the Docker image:

	docker build -t hello-server .

Run the server:

	docker run -p 8080:8080 -p 8081:8081 hello-server