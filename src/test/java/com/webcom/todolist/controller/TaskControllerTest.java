package com.webcom.todolist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webcom.todolist.entity.Task;
import com.webcom.todolist.service.TaskServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest extends AbstractRestControllersTest {

    private static final String URI = "/task";
    private static final String TASK_NAME = "taskName";
    private static final Long ID = 1L;

    @Mock
    private TaskServiceImpl service;
    @InjectMocks
    private TaskController controller;

    @Before
    public void setUp() {
        standaloneSetup(controller);
    }

    @Test
    public void testGetManagerTaskList() {
        when(service.getAll()).thenReturn(emptyList());

        given()
                .when()
                .get(URI)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo("[]")));
    }

    @Test
    public void testGetOneManagerTaskList() {
        final Task task = getTask(ID, TASK_NAME);
        final String expected = "{\"taskId\":1,\"taskName\":\"taskName\",\"isDone\":false}";

        when(service.getById(ID)).thenReturn(task);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(expected)));
    }

    @Test
    public void testCreateManagerTaskList() throws JsonProcessingException {
        final Task postTask = getTask(null, TASK_NAME);
        final Task createdTask = getTask(ID, TASK_NAME);
        final String expected = "{\"taskId\":1,\"taskName\":\"taskName\",\"isDone\":false}";

        when(service.create(postTask)).thenReturn(createdTask);

        given()
                .body(mapToJson(postTask))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .post(URI)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(expected)));
    }

    @Test
    public void testUpdateManagerTaskList() throws JsonProcessingException {
        final String taskNameUpdated = "taskNameUpdated";
        final Task putTask = getTask(ID, TASK_NAME, true);
        final Task updatedTask = getTask(ID, taskNameUpdated, true);
        final String expected = "{\"taskId\":1,\"taskName\":\"taskNameUpdated\",\"isDone\":true}";

        when(service.update(ID, putTask)).thenReturn(updatedTask);

        given()
                .body(mapToJson(putTask))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .put(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(expected)));
    }

    @Test
    public void testDeleteManagerTaskList()  {

        controller.delete(ID);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .delete(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());
    }

    private Task getTask(Long id, String taskName) {
        Task task = new Task();
        task.setTaskId(id);
        task.setTaskName(taskName);
        return task;
    }

    private Task getTask(Long id, String taskName, Boolean isDone) {
        Task task = getTask(id, taskName);
        task.setDone(isDone);
        return task;
    }
}