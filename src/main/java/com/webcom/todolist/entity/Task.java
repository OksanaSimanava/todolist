package com.webcom.todolist.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.webcom.todolist.view.Views;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@Table
public class Task {

    @Id
    @GeneratedValue(strategy = AUTO)
    @JsonView(Views.TodoList.class)
    @Column(unique = true, nullable = false, updatable = false)
    private Long taskId;

    @JsonView(Views.TodoList.class)
    private String taskName;

    @JsonView(Views.TodoList.class)
    private boolean isDone;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    @JsonView(Views.FullTodoList.class)
    private ManagerTaskList managerTaskList;
}
