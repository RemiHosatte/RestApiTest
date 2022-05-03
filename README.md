# RestApiTest

This API can register a new user and show the detail of a registered user 

## POST - Add new user 
Request: /addUser
Body example: 
{
    "username": "userTest",
    "birthdate": "2004-05-03T00:00:00.000Z",
    "country": "France",
    "phone": "0605441155",
    "gender": "Male"
}


## GET - Get user by id 
/{id}

## GET - Get user by username 
/{username}

For more details check the postman collection 


## For build the project 
Clone 
Type the command : "mvn clean install"
Type the command : "mvn spring-boot:run"
