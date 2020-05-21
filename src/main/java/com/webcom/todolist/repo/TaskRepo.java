package com.webcom.todolist.repo;

import com.webcom.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
