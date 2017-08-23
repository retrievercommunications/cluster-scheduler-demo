# Retriever Cluster Scheduler Demo - Kubernetes

## Getting started

TODO: 
	- setup instructions for minikube
	- setup and configuration for a private registry? (should instead just host the images publically on Docker Hub)

To set up on GKE:
- Create a container cluster
- Configure kubectl to use the cluster: kubectl config set-cluster <cluster-name>
- When the service is running, get an external IP to use for the curl request using: gcloud compute instances list 

## Usage

To spin up the Config Map defining all of the variables used by the hello-server:

	kubectl create -f config_maps/hello-server.yml 

To spin up a single pod hosting the hello-server application:

	kubectl create -f pods/hello-server.yml

To expose the pod on the minikube VM:
	
	kubectl create -f services/hello-server.yml

To test that the application is running and accessible:

	curl -X GET http://<MINIKUBE_NODE_IP>:31000/hello

To remove the pod:

	kubectl delete pod hello-server

To spin up multiple instances of the pod based on the deployment configuration:
	
	kubectl create -f deployments/hello-server.yml

To check that the pods will restart when the liveness health-checks are failing, set the "greetingLimit" in /apps/hello-server/config.yml to a very low number, and send multiple requests to the /hello endpoint using the service defined above. When the greetingLimit is reached, the applications healthcheck endpoint will begin to return error responses, at which point Kubernetes will automatically restart it. To check how many times the pod has been restarted, run the following command:

	kubectl get pods
