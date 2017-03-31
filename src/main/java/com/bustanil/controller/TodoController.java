package com.bustanil.controller;

import com.bustanil.model.Todo;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@Controller
@ViewScoped
public class TodoController {

    private List<Todo> todoList = new ArrayList<>();
    private String newTask;

    {
        todoList.add(new Todo("Todo 1"));
        todoList.add(new Todo("Todo 2", true));
        todoList.add(new Todo("Todo 3"));
        todoList.add(new Todo("Todo 4"));
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void addNewTask(ActionEvent e) {
        todoList.add(new Todo(newTask, false));
        newTask = "";
    }

    public void removeTask(ActionEvent event) {
        System.out.println("task to remove");
    }

    public String getNewTask() {
        return newTask;
    }

    public void setNewTask(String newTask) {
        this.newTask = newTask;
    }
}
