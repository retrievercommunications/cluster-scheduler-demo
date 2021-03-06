# Retriever Cluster Scheduler Demo - Amazon's ECS

## Getting started

To deploy containers to ECS, there are atleast 3 official ways using:

1. AWS Web Console - this is ok for the first time but not that suitable if you want to version control your files and use in a team environment.
2. [AWS CLI](http://docs.aws.amazon.com/AmazonECS/latest/developerguide/ECS_AWSCLI.html) - this is the 'ecs' subcommand in the standard AWS cli
3. [ECS CLI](https://github.com/aws/amazon-ecs-cli) This seemed like the nicest experience but currently it doesn't suppport windows so it has not been chosen.

## Required Amazon Background Info

In order to deploy to Amazon ECS, you unfortunately need to understand quite a bit of the AWS jargon.
Here is a very quick intro:

* EC2: A VM which runs in your cluster that containers get deployed to. You need to choose an AMI for your EC2 instance.
* AMI: Amazon Machine Image: a VM image containing the Operating System and any other required software
* EC2 Instance Type: pick 't2.micro' this is one of the smallest and it is included in free-tier and for a demo is plenty
* IAM Role - a set of permissions to access things within AWS. 
* Security Group (ASG) - basically firewall rules for your EC2 instance which tells which ports to open. by default only SSH is open inbound and all ports are open outbound. You'll need to open any ports that your container exposes on the host.

## ECS Concepts

### Clusters
A collection of EC2 instances that can run containers and that ECS can deploy tasks to.

### Task Definitions
They specify which container (and which version) to deploy.
You run a Task (based on a Task Definition) on a Cluster.
These are versioned.
A Task Definition family is a collection of versioned task definitions.

## Usage

### Creating the Cluster via Console

The easiest way to create the Cluster is through the console. 
    
    Just click on Getting Started, then click Cancel.
    Then click "create cluster" button. Give the cluster a name (e.g. "default").
    Change the "EC2 instance type" to 't2.micro'.
    Change the "Security group inbound rules" so that the "Port range" is 8080-8081 instead of 80.
    Leave everything else the default and click on Create button on bottom right of screen. 

Alternatively you can do this using the CLI but this requires a lot more AWS knowledge and steps.

### Creating the Cluster via AWS CLI

Create a cluster named 'default'

    aws ecs create-cluster

Create an EC2 instance and add to cluster

    //TODO 
    Should use the ECS optimised AMIs called 'Amazon ECS-optimized AMI'
    Make sure your EC2 instance has the AIM role ecsInstanceRole otherwise it won't be able to join the cluster


### Running the Container using AWS CLI

Register a task definiton

    aws ecs register-task-definition --cli-input-json file://tasks/hello-server.json

**Note**: If you want to modify the task definition json file, then you can re-register the task using the same command as above. 
It will automatically create a new version of the task in the same family.
When you run, just make sure you specify the right version number.

Run the task

    aws ecs run-task --cluster default --task-definition hello-server-family:1 --count 1

After a little while your container should be deployed and start running.

To access the demo app you'll need to go to view the Public DNS or IP of the single instance in the cluster in the EC2 console and then put ":8080/hello" at the end and open in a browser.

    e.g. http://ec2-52-63-164-215.ap-southeast-2.compute.amazonaws.com:8080/hello

**Note**: Normally people will put a load balancer in front so you wouldn't need to know the EC2 instance that it was deployed to.

If successful you should see:

    {"message":"Hello, World!"}