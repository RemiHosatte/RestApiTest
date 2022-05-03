
# RestApiTest

This API can register a new user and show the details of a registered user 

## POST - Add new user 
```/addUser```

Body example: 

```
{
    "username": "userTest",
    "birthdate": "2004-05-03T00:00:00.000Z",
    "country": "France",
    "phone": "0605441155",
    "gender": "Male"
}
```
Constraints:
- Country : France
- Adult only 

## GET - Get user by id 
```/{id}```

## GET - Get user by username 
```/{username}```



## For build the project 
Clone 
```
Type the command : "mvn clean install"
Type the command : "mvn spring-boot:run"
Default port : 8080
```

**For more details check the postman collection** 
