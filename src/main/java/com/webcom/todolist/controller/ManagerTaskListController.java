package com.webcom.todolist.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.webcom.todolist.entity.ManagerTaskList;
import com.webcom.todolist.service.ManagerTaskListServiceImpl;
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
@RequestMapping("managertasklist")
public class ManagerTaskListController {

        private final ManagerTaskListServiceImpl managerTaskListService;

        @Autowired
        public ManagerTaskListController(ManagerTaskListServiceImpl managerTaskListService) {
            this.managerTaskListService = managerTaskListService;
        }

        @GetMapping
        @JsonView(Views.FullTodoList.class)
        public List<ManagerTaskList> list() {
            return managerTaskListService.getAll();
        }

        @GetMapping("{id}")
        @JsonView(Views.FullTodoList.class)
        public ManagerTaskList getOne(@PathVariable("id") Long  id) {
            return managerTaskListService.getById(id);
        }

        @PostMapping
        @JsonView(Views.FullTodoList.class)
        public ManagerTaskList create(@RequestBody @Valid ManagerTaskList managerTaskList) {
            return managerTaskListService.create(managerTaskList);
        }

        @PutMapping("{id}")
        @JsonView(Views.FullTodoList.class)
        public ManagerTaskList update(@PathVariable("id") Long id, @RequestBody @Valid ManagerTaskList managerTaskList) {
            return managerTaskListService.update(id, managerTaskList);
        }

        @DeleteMapping("{id}")
        public void delete(@PathVariable("id") Long id) {
            managerTaskListService.delete(id);
        }
}
