# spring-flux

### Objective

Validation of good practices, design patterns and knowledge of Spring Boot.
### Statement

Develop a microservice in Spring Boot with WebFlux, functional endpoints and their unit tests, following 
the conventions of a REST service.

It will have two endpoints, one to save objects and another to list all objects. The service should use 
DTOs for communication with the outside world.

The microservice will save the data in MongoDB, using Docker. We will have two objects, the first will 
have id and name (nodeRoot) and the second will have these same attributes and, in addition, a 
description (nodeDesc). We want both objects to be stored in the same collection called “node”.
