<a href="https://medium.com/@taesulee93/mongodb-%EB%8F%84%ED%81%90%EB%A8%BC%ED%8A%B8-%EB%AA%A8%EB%8D%B8%EB%A7%81-8b536296c051">MongoDB 도큐먼트 모델링</a>

```shell
docker run --name mongo-server -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=taesu -e MONGO_INITDB_ROOT_PASSWORD=password mongo:6.0.13  
```


```javascript
db.demoApp;

use admin;
db.createUser(
    {
        user: "taesu",
        pwd: "password",
        roles: [{role: "userAdminAnyDatabase", db: "admin"},
            {role: "readWrite", db: "demoApp"}]
    }
    )

```
