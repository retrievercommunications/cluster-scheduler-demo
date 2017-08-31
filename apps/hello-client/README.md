# hello-client

The hello-client is a simple Go client for the hello-server that polls a given URL and prints the response, along with the server name contained in the Server header. This helps to identify which instances served a given request when the request passes through a load-balancing service.

## Getting started

Build the hello-client executable.

	make

Start an instance of the client by passing in an endpoint URL, along with an optional flag defining the poll period (in milliseconds)

	./hello-client -p <POLL_PERIOD_IN_MILLIS> <HELLO_SERVER_ENDPOINT>

## Running through Docker

Build a hello-client executable that is compatible with the scratch image by running the Makefile.
For Windows users, the `make` command can be added to an existing installation of Git Bash by following [these instructions](https://gist.github.com/evanwill/0207876c3243bbb6863e65ec5dc3f058).

	make docker-hello-client

Create the docker image

	docker build -t hello-client .

Run an instance of the client

	docker run hello-client -p <POLL_PERIOD_IN_MILLIS> <HELLO_SERVER_ENDPOINT>

