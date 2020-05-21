package com.webcom.todolist.service;

import com.webcom.todolist.entity.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    void delete(Long id);

    List<Task> getAll();

    Task update(Long taskId, Task task);

    Task create(Task task);
}
