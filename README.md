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
