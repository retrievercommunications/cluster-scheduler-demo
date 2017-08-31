hello-client: client.go
	go build -o hello-client

docker-hello-client: client.go
	CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o hello-client .