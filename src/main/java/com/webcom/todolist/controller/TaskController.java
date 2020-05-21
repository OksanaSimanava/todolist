package com.webcom.todolist.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.webcom.todolist.entity.Task;
import com.webcom.todolist.service.TaskServiceImpl;
import com.webcom.todolist.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskServiceImpl taskService;

    @Autowired
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @JsonView(Views.TodoList.class)
    public List<Task> list() {
        return taskService.getAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.TodoList.class)
    public Task getOne(@PathVariable("id") Long  id) {
        return taskService.getById(id);
    }

    @PostMapping
    @JsonView(Views.TodoList.class)
    public Task create(@RequestBody @Valid Task task) {
        return taskService.create(task);
    }

    @PutMapping("{id}")
    @JsonView(Views.TodoList.class)
    public Task update(@PathVariable("id") Long  id, @RequestBody @Valid Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        taskService.delete(id);
    }
}
