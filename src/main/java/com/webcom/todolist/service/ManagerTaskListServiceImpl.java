package com.webcom.todolist.service;

import com.webcom.todolist.entity.ManagerTaskList;
import com.webcom.todolist.exceptions.NotFoundException;
import com.webcom.todolist.repo.ManagerTaskListRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class ManagerTaskListServiceImpl {

    private final ManagerTaskListRepo managerTaskListRepo;

    @Autowired
    public ManagerTaskListServiceImpl(ManagerTaskListRepo managerTaskListRepo) {
        this.managerTaskListRepo = managerTaskListRepo;
    }

    public List<ManagerTaskList> getAll() {
        log.info("IN ManagerTaskListServiceImpl getAll");
        return managerTaskListRepo.findAll();
    }

    public ManagerTaskList getById(Long id) {
        log.info("IN ManagerTaskListServiceImpl getTaskList {} ", id);
        return managerTaskListRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public ManagerTaskList create(ManagerTaskList taskList) {
        log.info("IN ManagerTaskListServiceImpl create {} ", taskList);
        taskList.setTasks(null);
        return managerTaskListRepo.save(taskList);
    }

    @Transactional
    public ManagerTaskList update(Long taskListId, ManagerTaskList taskList) {
        log.info("IN ManagerTaskListServiceImpl update by id {} , {} ", taskListId, taskList);
        ManagerTaskList taskListFromDb = managerTaskListRepo.findById(taskListId).orElseThrow(NotFoundException::new);
        copyProperties(taskList, taskListFromDb, "id");
        return managerTaskListRepo.save(taskListFromDb);
    }

    public void delete(Long id) {
        log.info("IN ManagerTaskListServiceImpl delete id = {} ", id);
        managerTaskListRepo.deleteById(id);
    }
}
