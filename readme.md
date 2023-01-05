**Springboot 3 with Java 17 and ElasticSearch 8**

A quick start to get this working.

There is a _docker-compose_ file to start up the ES cluster (with 1 node):
docker-compose up --build

You can access Kibana on http://localhost:5601/
and Elastic on: http://localhost:9200

Get all users: GET http://localhost:8080/users
Get user John: GET http://localhost:8080/users/John

Delete user  DELETE http://localhost:8080/users/1

