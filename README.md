# Smart Campus Sensor & Room Management API

**Student:** Yahani Chalakshana Dissanayake  
**Student ID:** w2149629 / 20240101
**Module:** 5COSC022C.2 - Client-Server Architectures  
**University:** Informatics Institute of Technology, affiliated with the University of Westminster  

---

## Overview

This is my coursework project for the Client-Server Architectures module. I built a RESTful API using JAX-RS and Jersey that manages rooms and sensors for a university smart campus system. The API allows you to create rooms, register sensors inside those rooms, and record sensor readings over time.

I used Jersey 2.41 as the JAX-RS implementation and deployed everything on Apache Tomcat 9. All data is stored in memory using ConcurrentHashMap - no database was used, as per the coursework requirements.

The main things the API can do:
- Manage rooms (create, list, get by ID, delete)
- Register sensors and link them to rooms
- Filter sensors by type
- Record and retrieve sensor readings
- Handle errors properly without exposing stack traces

---

## Technologies Used

- Java (JDK 25)
- JAX-RS with Jersey 2.41 (javax namespace)
- Jackson for JSON
- Apache Tomcat 9
- Apache Maven
- Apache NetBeans 28
- Postman for testing

---

## How to Build and Run

### What you need installed
- JDK 11 or higher
- Apache Maven
- Apache Tomcat 9
- Apache NetBeans (recommended)

### Running in NetBeans

1. Clone the repository:
   ```bash
   git clone https://github.com/Yahani-Chalakshana/SmartCampusAPI.git
   ```
2. Open NetBeans and go to **File - Open Project**, then select the SmartCampusAPI folder
3. Make sure Tomcat 9 is added as the server (Tools - Servers - Add Server if not already there)
4. Right-click the project - **Clean and Build** - wait for BUILD SUCCESS
5. Right-click the project - **Run**
6. The browser should open at `http://localhost:8080/SmartCampusAPI/`
7. To test the API go to `http://localhost:8080/SmartCampusAPI/api/v1`

### Running with Maven manually

```bash
mvn clean install
```

Then copy the generated `SmartCampusAPI.war` from the `target/` folder into your Tomcat `webapps/` directory and start Tomcat.

---

## API Endpoints

All endpoints are under the base URL: `http://localhost:8080/SmartCampusAPI/api/v1`

| Method | Endpoint | What it does |
|---|---|---|
| GET | `/` | Discovery - returns API info and links |
| GET | `/rooms` | Get all rooms |
| POST | `/rooms` | Create a new room |
| GET | `/rooms/{roomId}` | Get a specific room |
| DELETE | `/rooms/{roomId}` | Delete a room (fails if sensors are still in it) |
| GET | `/sensors` | Get all sensors (can filter with ?type=) |
| POST | `/sensors` | Register a new sensor |
| GET | `/sensors/{sensorId}` | Get a specific sensor |
| GET | `/sensors/{sensorId}/readings` | Get reading history for a sensor |
| POST | `/sensors/{sensorId}/readings` | Add a new reading |

---

## Sample curl Commands

### 1. Discovery endpoint
```bash
curl -i http://localhost:8080/SmartCampusAPI/api/v1
```
Response:
```json
{
  "name": "Smart Campus Sensor & Room Management API",
  "version": "v1",
  "contact": {
    "owner": "Smart Campus Yahani",
    "email": "w2149629@westminster.ac.uk"
  },
  "resources": {
    "self": "/api/v1",
    "rooms": "/api/v1/rooms",
    "sensors": "/api/v1/sensors"
  }
}
```

### 2. Create a room
```bash
curl -i -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":50}"
```
Response (201 Created):
```json
{
  "id": "LIB-301",
  "name": "Library Quiet Study",
  "capacity": 50,
  "sensorIds": []
}
```

### 3. Register a sensor
```bash
curl -i -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d "{\"id\":\"TEMP-001\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":0.0,\"roomId\":\"LIB-301\"}"
```
Response (201 Created):
```json
{
  "id": "TEMP-001",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 0.0,
  "roomId": "LIB-301"
}
```

### 4. Add a sensor reading
```bash
curl -i -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings \
  -H "Content-Type: application/json" \
  -d "{\"value\":22.5}"
```
Response (201 Created):
```json
{
  "id": "a1b2c3d4-...",
  "timestamp": 1713686400000,
  "value": 22.5
}
```

### 5. Filter sensors by type
```bash
curl -i "http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=Temperature"
```

### 6. Get sensor reading history
```bash
curl -i http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings
```

### 7. Try to delete a room that still has sensors (409 error)
```bash
curl -i -X DELETE http://localhost:8080/SmartCampusAPI/api/v1/rooms/LIB-301
```
Response (409 Conflict):
```json
{
  "error": "409 Conflict",
  "message": "Room LIB-301 still has 1 sensor(s) assigned. Remove sensors first.",
  "hint": "Remove all sensors from the room before deleting it."
}
```

### 8. Register sensor with a room that doesn't exist (422 error)
```bash
curl -i -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d "{\"id\":\"CO2-999\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":0.0,\"roomId\":\"FAKE-999\"}"
```
Response (422 Unprocessable Entity):
```json
{
  "error": "422 Unprocessable Entity",
  "message": "Room with ID 'FAKE-999' does not exist. Cannot register sensor.",
  "hint": "Ensure the roomId in your request body refers to an existing room."
}
```

### 9. Post a reading to a sensor in MAINTENANCE (403 error)
```bash
curl -i -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/CO2-001/readings \
  -H "Content-Type: application/json" \
  -d "{\"value\":450.0}"
```
Response (403 Forbidden):
```json
{
  "error": "403 Forbidden",
  "message": "Sensor CO2-001 is under MAINTENANCE and cannot accept new readings.",
  "hint": "Change sensor status to ACTIVE before posting readings."
}
```

### 10. Get all rooms
```bash
curl -i http://localhost:8080/SmartCampusAPI/api/v1/rooms
```

### 11. Get a specific room by ID
```bash
curl -i http://localhost:8080/SmartCampusAPI/api/v1/rooms/LIB-301
```

---

## Error Handling

I implemented custom exception mappers for all the error scenarios in the coursework. The API never returns a raw Java stack trace - all errors return a JSON body with a clear message.

| Situation | HTTP Status |
|---|---|
| Trying to delete a room that still has sensors | 409 Conflict |
| Registering a sensor with a roomId that doesn't exist | 422 Unprocessable Entity |
| Posting a reading to a sensor in MAINTENANCE | 403 Forbidden |
| Any unexpected server error | 500 Internal Server Error |

---

## Report - Answers to Coursework Questions

### Part 1.1 - JAX-RS Resource Lifecycle

By default, JAX-RS creates a new instance of each resource class for every incoming HTTP request. This is called request-scoped lifecycle. Once the response is sent, the instance is thrown away. This means you cannot store any data inside a resource class as an instance variable, because it will be lost after every single request.

To get around this, I created a separate `DataStore` class that uses the Singleton pattern. Only one instance of it ever exists, and all resource classes access it using `DataStore.getInstance()`. This way, the data stays in memory as long as the server is running.

Since Tomcat handles multiple requests at the same time on different threads, I used `ConcurrentHashMap` instead of a regular `HashMap`. This prevents race conditions where two requests might try to update the same data at the same time and corrupt it.

---

### Part 1.2 - HATEOAS

HATEOAS means the API includes links in its responses so clients can navigate without needing to know all the URLs in advance. This is useful because clients can just start at the discovery endpoint and follow links to find rooms, sensors, and other resources, rather than relying on external documentation that might be out of date.

In my API, `GET /api/v1` returns links to `/api/v1/rooms` and `/api/v1/sensors`. This means a developer integrating with the API does not need to guess or hardcode those paths - the server tells them. Compared to static documentation, this approach ensures the API itself is always the accurate source of truth.

---

### Part 2.1 - Returning IDs vs Full Objects

Returning only room IDs would make the response smaller and save bandwidth, which is good when you have thousands of rooms. But it means the client has to make a separate request for every single room to get its details, which creates a lot of extra network calls. This is called the N+1 problem.

Returning full room objects means the response is bigger, but the client gets everything it needs in one go. In my implementation I return full objects because the data set is small and it keeps the client code much simpler.

---

### Part 2.2 - Is DELETE Idempotent?

Yes, DELETE is idempotent in my implementation. Idempotent means calling the same request multiple times ends up with the same result on the server.

If you send `DELETE /rooms/LIB-301` and the room exists with no sensors, it gets deleted and you get 200 OK. If you send the exact same request again, the room is already gone so you get 404 Not Found. The response code is different, but the server state is the same both times — the room does not exist. So the operation is idempotent.

---

### Part 3.1 - @Consumes and Content-Type Mismatch

The `@Consumes(MediaType.APPLICATION_JSON)` annotation tells JAX-RS that the method will only accept requests with a `Content-Type: application/json` header. If a client sends `text/plain` or `application/xml` instead, JAX-RS rejects the request before it even reaches my method and returns a `415 Unsupported Media Type` response automatically. This means I do not need to write any extra validation code myself - the framework handles it.

---

### Part 3.2 - @QueryParam vs Path Parameter for Filtering

Using `?type=CO2` as a query parameter is better than putting the type in the path like `/sensors/type/CO2` because query parameters are meant for optional filtering, not for identifying resources. The base resource `/api/v1/sensors` still makes sense with or without the filter applied.

If the type was in the path, it would imply that `CO2` is a sub-resource of `type`, which is not true — it is just a filter condition. Query parameters are also much easier to extend. For example, you could easily add `?type=CO2&status=ACTIVE` without changing the endpoint structure at all.

---

### Part 4.1 - Sub-Resource Locator Pattern

Instead of putting all the readings logic directly inside `SensorResource`, I used a sub-resource locator to delegate requests to `/sensors/{id}/readings` to a separate `SensorReadingResource` class. The locator method just returns a new instance of that class, and JAX-RS handles the rest.

This keeps the code cleaner and easier to maintain. Each class has one job - `SensorResource` handles sensors, `SensorReadingResource` handles readings. If I need to change something about how readings work, I only touch one class. If everything was in one big controller class, it would get very messy and hard to read as the API grows.

---

### Part 5.2 — 422 vs 404

When a client tries to register a sensor with a `roomId` that does not exist, the URL they are calling (`/api/v1/sensors`) is valid and works fine. So returning 404 would be confusing because nothing about the URL is missing - the problem is inside the request body.

422 Unprocessable Entity is more accurate here because it means "I understood your request and the format is fine, but the data inside it has a logic problem." In this case, the roomId they referenced does not exist. It makes it much clearer to the developer what went wrong.

---

### Part 5.4 - Risks of Exposing Stack Traces

If a Java stack trace gets sent back to the client, it can reveal a lot of sensitive information. An attacker could see the exact class and method names, the package structure of the application, which frameworks and library versions are being used, and even the file paths on the server. With that information they could look up known vulnerabilities for those specific versions or understand the internal logic of the app to find ways to exploit it.

In my API the `GlobalExceptionMapper` catches any unexpected errors and just returns a plain 500 message without any technical details, so none of that internal information is exposed.

---

### Part 5.5 - Filters vs Manual Logging

If I added a `Logger.info()` line inside every single resource method, I would end up with the same logging code repeated dozens of times. That is hard to maintain - if I want to change the log format I have to update every method.

Using a JAX-RS filter means the logging code is written once in `LoggingFilter.java` and it automatically runs for every request and every response. It is cleaner, more consistent, and much easier to change later. It also keeps the resource methods focused on their actual job rather than mixing in logging code.

---

## GitHub Repository

https://github.com/Yahani-Chalakshana/SmartCampusAPI

---

## Notes

- Only JAX-RS was used - no Spring Boot
- No database - all data is in memory using ConcurrentHashMap
- Data resets when the server restarts
- Tested using Postman
