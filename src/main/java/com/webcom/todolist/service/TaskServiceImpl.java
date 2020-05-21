package com.webcom.todolist.service;

import com.webcom.todolist.entity.Task;
import com.webcom.todolist.exceptions.NotFoundException;
import com.webcom.todolist.repo.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public Task getById(Long id) {
        log.info("IN TaskServiceImpl getById {}", id);
        return taskRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        log.info("IN TaskServiceImpl delete {}", id);
        taskRepo.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        log.info("IN TaskServiceImpl getAll");
        return taskRepo.findAll();
    }

    @Override
    public Task create(Task task) {
        log.info("IN TaskServiceImpl create {}", task);

        try {
            taskRepo.save(task);
        }catch (DataIntegrityViolationException e){
            long id = task.getManagerTaskList().getId();
            log.info("not found taskManagerList id =  {}", id);
            throw new NotFoundException("not found taskManagerList id = " + task.getManagerTaskList().getId());
        }
        return task;
    }

    @Override
    @Transactional
    public Task update(Long taskId, Task task) {
        log.info("IN TaskServiceImpl update by id {} , {} ", taskId, task);
        Task taskFromDb = taskRepo.findById(taskId).orElseThrow(NotFoundException::new);
        copyProperties(task, taskFromDb, "taskId");
        return taskRepo.save(taskFromDb);
    }
}
