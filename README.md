# Dogcam

A sample multi-module project showcasing Kotlin on the server

A server application for supplying dog pictures to Slack. 


## Features

- HTTP API server implemented using [http4k](https://www.http4k.org)
- [gRPC](https://grpc.io/) implementation 
- HTTP testing using [Spek](http://spekframework.org/) & [Rest Assured](http://rest-assured.io/)
- *Vaguely* Domain Driven Design 

## Running the Applications

	# Start the HTTP service
	gradlew api:run
	
	# Start the gRPC service
	gradlew picture-fetcher:run


## Module Breakdown

- **domain** Domain classes and business logic
- **infrastructure** Implementation of the domain module
- **proto** gRPC proto definition. Also contains some coroutine extension methods 
- **picture-fetcher** gRPC server for fetching dog pictures
- **api** HTTP Server. Entry point for Slack webhook



