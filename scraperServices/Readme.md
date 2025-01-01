# Spring Boot ActiveMQ Demo

## Overview

This project is a Spring Boot application that integrates with ActiveMQ for messaging. It demonstrates how to fetch, process, and publish data using Spring Boot, ActiveMQ, and JSON processing.

## Features

- \*\*Fetch Data\*\*: Uses the `Scraper` class to fetch data from an external API.
- \*\*Process Data\*\*: Parses JSON data and converts it into `Ad` objects.
- \*\*Publish Messages\*\*: Publishes `Ad` objects to an ActiveMQ queue.
- \*\*Configuration\*\*: Uses properties from the `application.properties` file for configuration.

## Prerequisites

- Java 8 or higher
- Maven
- ActiveMQ

## Setup

1. \*\*Clone the repository\*\*:
   \```sh
   git clone <repository-url>
   cd <repository-directory>
   \```

2. \*\*Configure ActiveMQ\*\*:
   Ensure ActiveMQ is running and accessible.

3. \*\*Update `application.properties`\*\*:
   Configure the necessary properties in `src/main/resources/application.properties`:
   \```properties
   spring.activemq.broker-url=tcp://localhost:61616
   spring.activemq.user=admin
   spring.activemq.password=admin
   spring.activemq.packages.trust-all=true
   server.port=8083
   api.key=tH_JPiY0Y3fL
   project.token=tHHkvRCiudLU
   base.url=https://www.parsehub.com/api/v2
   jms.queue.name=AdsQueue
   \```

## Build and Run

1. \*\*Build the project\*\*:
   \```sh
   mvn clean install
   \```

2. \*\*Run the application\*\*:
   \```sh
   mvn spring-boot:run
   \```

## Usage

### Fetch and Process Data

The `Scraper` class fetches data from an external API using an HTTP GET request. The fetched data is processed in the `SpringBootActiveMqDemoApplication` class.

### Publish Messages

The `PublishController` provides endpoints to publish messages to the ActiveMQ queue.

- \*\*Endpoint\*\*: `/api/publish`
- \*\*Method\*\*: POST
- \*\*Parameters\*\*:
  - `message` (required): The message to be published.
  - `queueName` (optional): The name of the queue.

Example:
\```sh
curl -X POST "http://localhost:8083/api/publish?message=TestMessage"
\```

- \*\*Endpoint\*\*: `/api/publish/ad`
- \*\*Method\*\*: POST
- \*\*Parameters\*\*:
  - `ad` (required): The Ad object to be published.
  - `queueName` (optional): The name of the queue.

Example:
\```sh
curl -X POST -H "Content-Type: application/json" -d '{"name":"Example Ad","code_zimmo":"12345"}' "http://localhost:8083/api/publish/ad"
\```

## Project Structure

- `src/main/java/com/bridgingcode/springbootactivemqdemo`:
  - `SpringBootActiveMqDemoApplication.java`: Main application class.
  - `Scraper.java`: Class to fetch data from an external API.
  - `model/Ad.java`: Model class for Ad objects.
  - `publisher/controller/PublishController.java`: Controller to publish messages to ActiveMQ.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.