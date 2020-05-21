package com.webcom.todolist.repo;

import com.webcom.todolist.entity.ManagerTaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerTaskListRepo extends JpaRepository<ManagerTaskList, Long> {
}
