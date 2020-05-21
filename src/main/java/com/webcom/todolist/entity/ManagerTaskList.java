package com.webcom.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webcom.todolist.view.Views;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table
public class ManagerTaskList {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @JsonView(Views.FullTodoList.class)
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonView(Views.FullTodoList.class)
    private String taskListName;

    @OneToMany(mappedBy = "managerTaskList", fetch = LAZY, cascade = ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JsonSerialize(using = CustomManagerTaskListSerializer.class)
    @JsonView(Views.FullTodoList.class)
    private List<Task> tasks;


}
