EXAM 2024 - 3. Semester

# Exam Description
Build a backend system for an educational institution to manage and track items like cameras, 
microphones etc, in the medialab and makerlab. Tasks include managing students and their 
borrowed items. Theoretical questions are part of the exercise.

# Domain description
The application manages items and their borrowing status.

my-javalin-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dk/studiofavrholdt/
│   │   │   │   ├── config/
│   │   │   │   │   └── HibernateConfig.java
│   │   │   │   ├── controllers/
│   │   │   │   ├── daos/
│   │   │   │   ├── dtos/
│   │   │   │   ├── entities/
│   │   │   │   ├── enums/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── routes/
│   │   │   │   ├── security/
│   │   │   │   ├── utils/
│   │   │   │   ├── Main.java
│   │   └── resources/
│   │       └── http/
│   │       │   └── dev.http
│   │       ├── config.properties
│   │       ├── logback.xml
│   └── test/
├── pom.xml
├── .gitignore
├── README.md


Item:
Attributes: id, name, purchasePrice, category, acquisitionDate, description.
Category: Enum (VIDEO, VR, SOUND, PRINT, TOOL).

Student:
Attributes: id, name, email, enrollmentDate, phone, itemList.
A student can borrow multiple items, but each item can belong to only one student.

# About 
This is a simple REST API for a ??? system.
The project is implemented using Javalin and JPA. The database is a Postgres database.

# Technical Documentation
- IntelliJ setup with Maven, Javalin and JPA
- JPA dependencies
- Lombok
- Junit 5
- Postgres drivers
- Test containers

Project is created for Java 17 corretto
config.properties is used for database connection and token security
gitignore is used to exclude target folder and config.properties

### Tests
The DAO layer is tested using JUnit and Mockito. The tests can be found in the test folder.
The endpoints are tested using JUnit and RestAssured. The tests can be found in the test folder.

### Example JSON Error Responses
In case of an error, the API returns a JSON object with the following structure:

- `message`: A description of the error.
- `status`: The HTTP status code indicating the type of error.
- `timestamp`: The exact time when the error occurred, useful for debugging.

### How to run
1. Create a database in your local Postgres instance called `exam`
2. Add a `config.properties` file in the resources folder with the following content:
```
SECRET_KEY=YOUR SECRET KEY
ISSUER=YOUR NAME
TOKEN_EXPIRE_TIME=1800000
DB_NAME=YOUR DB NAME

```
3. Run the main method in the config.Populate class to populate the database with some data
4. Run the main method in the Main class to start the server on port 7070
5. See the routes in your browser at `http://localhost:7076/routes`
6. Use the dev.http file to test the routes, GET/POST/PUT/DELETE 

### Endpoint Overview
This API provides CRUD operations. Each endpoint returns JSON responses and handles specific exceptions as documented below.

| HTTP Method | REST Resource                           | Success Status | Exceptions and Status(es)                                     |
|-------------|-----------------------------------------|----------------|--------------------------------------------------------------|
| GET         | `/items`                                | 200 OK         | 500 Internal Server Error if an unexpected error occurs      |
| GET         | `/items/{id}`                           | 200 OK         | 404 Not Found if ID does not exist, 400 Bad Request for invalid ID format, 500 Internal Server Error |
| POST        | `/items`                                | 201 Created    | 400 Bad Request if request body is invalid, 500 Internal Server Error |
| PUT         | `/items/{id}`                           | 200 OK         | 404 Not Found if ID does not exist, 400 Bad Request for invalid ID format, 500 Internal Server Error |
| DELETE      | `/items/{id}`                           | 200 OK         | 404 Not Found if ID does not exist, 400 Bad Request for invalid ID format, 500 Internal Server Error |
| PUT         | `/items/{itemId}/students/{studentId}`  | 200 OK         | 404 Not Found if itemId or studentId does not exist, 400 Bad Request for invalid ID format, 500 Internal Server Error |
| POST        | `/items/populate`                       | 201 Created    | 500 Internal Server Error if an unexpected error occurs      |

----------------------------------------------------------------------------------------

## Documentation of the API
Below is the API documentation for the API via dev.http file.

### A simple GET request to the test endpoint. No need for login
GET {{url}}/auth/test/

GET http://localhost:7076/api/auth/test/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:42:11 GMT
Content-Type: application/json
Content-Length: 34

{
"msg": "Hello Im just testing :)"
}

### Populate the database with some data
GET {{url}}/items/populate/

GET http://localhost:7076/api/items/populate/

HTTP/1.1 400 Bad Request
Date: Thu, 21 Nov 2024 09:43:11 GMT
Content-Type: application/json
Content-Length: 74

{
"id": [
{
"message": "TYPE_CONVERSION_FAILED",
"args": {},
"value": "populate"
}
]
}
Response file saved.
> 2024-11-21T104311.400.json

Response code: 400 (Bad Request); Time: 355ms (355 ms); Content length: 74 bytes (74 B)


### Create a new user - this one will only get a USER role by default
POST {{url}}/auth/register/

{
"username": "testuser",
"password": "testuser"
}

POST http://localhost:7076/api/auth/register/

HTTP/1.1 201 Created
Date: Thu, 21 Nov 2024 09:43:38 GMT
Content-Type: application/json
Content-Length: 230

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJGZWxpY2lhIEZhdnJob2xkdCIsInN1YiI6InRlc3R1c2VyIiwiZXhwIjoxNzMyMTg0MDE4LCJyb2xlcyI6IlVTRVIiLCJ1c2VybmFtZSI6InRlc3R1c2VyIn0.BlMPl6zblVRVJPmfBvM6qyuoaygGvaGEOAmDqLuiIqA",
"username": "testuser"
}
Response file saved.
> 2024-11-21T104338.201.json

Response code: 201 (Created); Time: 213ms (213 ms); Content length: 230 bytes (230 B)


###
POST {{url}}/auth/login/

{
"username": "testuser",
"password": "testuser"
}

> {%
client.global.set("jwt_token", response.body.token);
console.log("JWT Token:", client.global.get("jwt_token"));
%}
>

POST http://localhost:7076/api/auth/login/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:44:06 GMT
Content-Type: application/json
Content-Length: 230

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJGZWxpY2lhIEZhdnJob2xkdCIsInN1YiI6InRlc3R1c2VyIiwiZXhwIjoxNzMyMTg0MDQ2LCJyb2xlcyI6InVzZXIiLCJ1c2VybmFtZSI6InRlc3R1c2VyIn0.p0kGsOJ5CZeyE2TOEVq0nvM5etDCJcxj2DgPGNajUW8",
"username": "testuser"
}
Response file saved.
> 2024-11-21T104406.200.json

Response code: 200 (OK); Time: 126ms (126 ms); Content length: 230 bytes (230 B)


###
GET {{url}}/protected/user_demo/
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7076/api/protected/user_demo/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:44:31 GMT
Content-Type: application/json
Content-Length: 35

{
"msg": "Hello from USER Protected"
}
Response file saved.
> 2024-11-21T104431.200.json

Response code: 200 (OK); Time: 39ms (39 ms); Content length: 35 bytes (35 B)


###
GET {{url}}/protected/admin_demo/
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7076/api/protected/admin_demo/

HTTP/1.1 401 Unauthorized
Date: Thu, 21 Nov 2024 09:44:55 GMT
Content-Type: application/json
Content-Length: 184

{
"title": "Unauthorized with roles: [user]. Needed roles are: [ADMIN]",
"status": 401,
"type": "https://javalin.io/documentation#unauthorizedresponse",
"details": {}
}
Response file saved.
> 2024-11-21T104455.401.json

Response code: 401 (Unauthorized); Time: 41ms (41 ms); Content length: 184 bytes (184 B)


### Add admin role to user
POST {{url}}/auth/user/addrole/
Accept: application/json
Authorization: Bearer {{jwt_token}}

{
"role": "admin"
}

POST http://localhost:7076/api/auth/user/addrole/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:45:19 GMT
Content-Type: application/json
Content-Length: 34

{
"msg": "Role admin added to user"
}
Response file saved.
> 2024-11-21T104519.200.json

Response code: 200 (OK); Time: 56ms (56 ms); Content length: 34 bytes (34 B)


(after login again)

GET http://localhost:7076/api/protected/admin_demo/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:45:52 GMT
Content-Type: application/json
Content-Length: 36

{
"msg": "Hello from ADMIN Protected"
}
Response file saved.
> 2024-11-21T104552.200.json

Response code: 200 (OK); Time: 15ms (15 ms); Content length: 36 bytes (36 B)


---

### **Item Routes**

#### **Get All Items**
GET {{url}}/items/
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7076/api/items/

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:54:47 GMT
Content-Type: application/json
Content-Length: 305

[
{
"id": 1,
"name": "VR Headset",
"purchasePrice": 1200.0,
"category": "VR",
"acquisitionDate": [
2022,
3,
18
],
"description": "A high-quality VR headset.",
"studentId": 1
},
{
"id": 2,
"name": "Camera",
"purchasePrice": 850.0,
"category": "VIDEO",
"acquisitionDate": [
2021,
10,
5
],
"description": "A professional camera.",
"studentId": 2
}
]
Response file saved.
> 2024-11-21T105447.200.json

Response code: 200 (OK); Time: 386ms (386 ms); Content length: 305 bytes (305 B)


#### **Get Item by ID**
GET {{url}}/items/{id}
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7076/api/items/1

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:55:46 GMT
Content-Type: application/json
Content-Length: 154

{
"id": 1,
"name": "VR Headset",
"purchasePrice": 1200.0,
"category": "VR",
"acquisitionDate": [
2022,
3,
18
],
"description": "A high-quality VR headset.",
"studentId": 1
}
Response file saved.
> 2024-11-21T105546.200.json

Response code: 200 (OK); Time: 65ms (65 ms); Content length: 154 bytes (154 B)


#### **Add a New Item**
POST {{url}}/items/
Accept: application/json
Authorization: Bearer {{jwt_token}}

{
"name": "New Item",
"purchasePrice": 100.0,
"category": "TOOL",
"acquisitionDate": "2023-11-21",
"description": "A useful tool"
}

#### **Update an Item**
PUT {{url}}/items/{id}
Accept: application/json
Authorization: Bearer {{jwt_token}}

{
"name": "Updated Item",
"purchasePrice": 150.0,
"category": "TOOL",
"acquisitionDate": "2023-11-22",
"description": "An updated description"
}

PUT http://localhost:7076/api/items/1

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:02:17 GMT
Content-Type: application/json
Content-Length: 154

{
"id": 1,
"name": "Updated Item",
"purchasePrice": 150.0,
"category": "TOOL",
"acquisitionDate": [
2023,
11,
22
],
"description": "An updated description",
"studentId": 1
}
Response file saved.
> 2024-11-21T110217.200.json

Response code: 200 (OK); Time: 81ms (81 ms); Content length: 154 bytes (154 B)


#### **Delete an Item**
DELETE {{url}}/items/1
Accept: application/json
Authorization: Bearer {{jwt_token}}

#### **Assign an Item to a Student**
POST {{url}}/items/{itemId}/students/1
Accept: application/json
Authorization: Bearer {{jwt_token}}

#### **Get Items by Student**
GET {{url}}/students/1/items
Accept: application/json
Authorization: Bearer {{jwt_token}}

#### **Get Relevant Shops for a Category**
GET {{url}}/shops/SOUND
Accept: application/json
Authorization: Bearer {{jwt_token}}

#### **Filter Items by Category**
GET {{url}}/items/filter?category=SOUND
Accept: application/json
Authorization: Bearer {{jwt_token}}

#### **Summarize Total Purchase Price of Items Borrowed by Each Student**
GET {{url}}/students/items/summary
Accept: application/json
Authorization: Bearer {{jwt_token}}


## Task 4: REST Error Handling (5%)
4.1 Return JSON exceptions for at least:

### Fetching an item by ID if it does not exist.
GET {{url}}/items/111
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7076/api/items/111

HTTP/1.1 400 Bad Request
Date: Thu, 21 Nov 2024 09:59:33 GMT
Content-Type: application/json
Content-Length: 63

{
"status": 400,
"message": "Failed to fetch item",
"error": "Error"
}
Response file saved.
> 2024-11-21T105933.400.json

Response code: 400 (Bad Request); Time: 106ms (106 ms); Content length: 63 bytes (63 B)

//code from ItemController
} catch (ApiException e) {
log.error("Error fetching item by ID: {}", e.getMessage(), e);
ctx.status(e.getStatusCode()).json(Map.of(
"status", e.getStatusCode(),
"error", "Error",
"message", e.getMessage()
));

### Deleting an item that does not exist.

DELETE http://localhost:7076/api/items/111

HTTP/1.1 404 Not Found
Date: Thu, 21 Nov 2024 10:07:45 GMT
Content-Type: application/json
Content-Length: 68

{
"status": 404,
"message": "Item not found.",
"error": "Deletion Failed"
}
Response file saved.
> 2024-11-21T110745.404.json


### 8.3 Describe how to fix failing tests caused by role-based security or implement fixes in code.
The failing tests caused by role-based security can be fixed by adding the correct roles to the user.
we use Authorization: Bearer {{jwt_token}} to access the protected endpoints. 
The token is generated when the user logs in. The token contains the roles of the user.
The roles are checked in the protected endpoints to determine if the user has the correct role to access the endpoint.
If the user does not have the correct role, the endpoint returns a 401 Unauthorized response.
To fix the failing tests, we need to add the correct roles to the user. This can be done by calling the /auth/user/addrole/ endpoint with the correct role.
For example, to add the admin role to a user, we can call the /auth/user/addrole/ endpoint with the role "admin".
After adding the correct role to the user, the user will be able to access the protected endpoints that require the admin role.


# Backend_Exam_2024
