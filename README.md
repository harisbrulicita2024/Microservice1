# Jobs Portal Service 💼💵

This is the first microservice of the Jobs Portal system, developed using Spring Boot.

## Features:

* API endpoints for Create, Read, Update, and Delete operations on job posts.
* API endpoints for searching by location, job category, and salary range.
* API endpoint for retrieving suggested jobs based on location.

For a detailed overview of how this service interacts with others, please refer to [this diagram](https://i.imgur.com/u2xMrN3.png).

## Additional Components:
* Integration with ActiveMQ for messaging.
* PostgreSQL database running on port 5432.
* ActiveMQ's user-friendly interface can be accessed at port 8161.

## How to Use:

1. Ensure that Docker and Docker Compose are installed on your system.
2. Clone this repository to your local machine.
3. Navigate to the root directory of the cloned repository.
4. Run `docker-compose up` to start the microservice and its dependencies.
5. Once the service is up and running, you can access the API endpoints using tools like Postman or curl.
6. Access the Swagger UI for API documentation at [localhost:9000/swagger/index.html](localhost:9000/swagger/index.html).
