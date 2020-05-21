1. gradle build
2. gradle run

3. Access using next REST requests:

________________________________________Get
GET: localhost:8080/task - get all tasks
GET: localhost:8080/task/1 - get task with ID 1

GET: localhost:8080/managertasklist     - get all managertasklist
GET: localhost:8080/managertasklist/1   - get list with tasks where ID 1
_____________________________________________________Create
POST: http://localhost:8080/managertasklist
taskListName   should be unique - обработку ошибок не добавляла, т.к. нужно более подробное описание, как должна себя вести система
Request body:
{
    "taskListName": "q123"
}

POST:   http://localhost:8080/task
{
    "taskName": "taskNam_",
    "managerTaskList": 
    {
        "id": "1"           //уже должен содержаться managertasklist с таким id
    }

}
______________________________________________________Update
PUT: localhost:8080/managertasklist/1
{
    "id": 3,
    "taskListName": "taskListName123"
}

PUT: localhost:8080/task/1
{
    "taskName": "taskName",
    "isDone": "true"
}

_____________________________________________________Delete
DELETE: localhost:8080/managertasklist/1 - delete managertasklist with ID 1 and allsubtasks
DELETE: localhost:8080/task/5            - delete task from db and from managertasklist






