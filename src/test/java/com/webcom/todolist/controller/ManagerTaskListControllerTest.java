package com.webcom.todolist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webcom.todolist.entity.ManagerTaskList;
import com.webcom.todolist.entity.Task;
import com.webcom.todolist.service.ManagerTaskListServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class ManagerTaskListControllerTest extends AbstractRestControllersTest {

    private static final String URI = "/managertasklist";
    private static final String MANAGER_TASKLIST_NAME = "testManagerTaskListName";
    private static final Long ID = 1L;

    @Mock
    private ManagerTaskListServiceImpl service;
    @InjectMocks
    private ManagerTaskListController controller;

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
    public void testGetOneManagerTaskList() throws JsonProcessingException {
        final ManagerTaskList createdManagerTaskList = getManagerTaskList(ID, MANAGER_TASKLIST_NAME);

        when(service.getById(ID)).thenReturn(createdManagerTaskList);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(mapToJson(createdManagerTaskList))));
    }

    @Test
    public void testGetOneManagerTaskListWithTasks() throws JsonProcessingException {
        final Task task = getTask(ID, "taskName");
        final Task task2 = getTask(2L, "taskName2");

        final ManagerTaskList createdManagerTaskList = getManagerTaskList(ID, MANAGER_TASKLIST_NAME);
        createdManagerTaskList.setTasks(asList(task, task2));

        when(service.getById(ID)).thenReturn(createdManagerTaskList);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(mapToJson(createdManagerTaskList))));
    }

    @Test
    public void testCreateManagerTaskList() throws JsonProcessingException {
        final ManagerTaskList postManagerTaskList = getManagerTaskList(null, MANAGER_TASKLIST_NAME);
        final ManagerTaskList createdManagerTaskList = getManagerTaskList(ID, MANAGER_TASKLIST_NAME);

        when(service.create(postManagerTaskList)).thenReturn(createdManagerTaskList);

        given()
                .body(mapToJson(postManagerTaskList))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .post(URI)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(mapToJson(createdManagerTaskList))));
    }

    @Test
    public void testUpdateManagerTaskList() throws JsonProcessingException {
        final String managerTaskListNameUpdated = "testManagerTaskListNameUpdated";
        final ManagerTaskList putManagerTaskList = getManagerTaskList(ID, MANAGER_TASKLIST_NAME);
        final ManagerTaskList updatedManagerTaskList = getManagerTaskList(ID, managerTaskListNameUpdated);

        when(service.update(ID, putManagerTaskList)).thenReturn(updatedManagerTaskList);

        given()
                .body(mapToJson(putManagerTaskList))
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .put(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value())
                .contentType(APPLICATION_JSON_VALUE)
                .body(is(equalTo(mapToJson(updatedManagerTaskList))));
    }

    @Test
    public void testDeleteManagerTaskList() {

        controller.delete(ID);

        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .delete(URI + "/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());
    }

    private ManagerTaskList getManagerTaskList(Long id, String taskListName) {
        ManagerTaskList managerTaskList = new ManagerTaskList();
        managerTaskList.setTaskListName(taskListName);
        managerTaskList.setId(id);
        return managerTaskList;
    }

    private Task getTask(Long id, String taskName) {
        Task task = new Task();
        task.setTaskId(id);
        task.setTaskName(taskName);
        return task;
    }
}