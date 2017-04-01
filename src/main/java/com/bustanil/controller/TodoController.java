package com.bustanil.controller;

import com.bustanil.model.Todo;
import com.bustanil.repository.TodoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@ViewScoped
@Transactional
public class TodoController implements InitializingBean {

    private List<Todo> todoList = new ArrayList<>();
    private String newTask;
    private boolean selectAll;

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void addNewTask() {
        Todo newTodo = new Todo(newTask, false);
        todoRepository.save(newTodo);
        todoList.add(newTodo);
        newTask = "";
    }

    public void removeTask(Todo todo) {
        todoRepository.delete(todo);
        todoList.remove(todo);
    }

    public void updateTask(AjaxBehaviorEvent event) {
        Long todoId = (Long) event.getComponent().getAttributes().get("todoId");
        Todo todo = todoList.stream().filter(t -> t.getId().equals(todoId)).findFirst().get();
        todoRepository.save(todo);
    }

    public void clearCompleted(){
        todoRepository.delete(todoList.stream().filter(Todo::getCompleted).collect(Collectors.toList()));
        todoList = todoList.stream().filter(todo -> !todo.getCompleted()).collect(Collectors.toList());
    }

    public void selectAll(AjaxBehaviorEvent event){
        for (Todo todo : todoList) {
            todo.setCompleted(isSelectAll());
        }
    }

    public void setFilter(final String filter) {
        todoList = todoRepository.findAll();
        todoList = todoList.stream().filter(todo -> {
            if ("All".equals(filter)) {
                return true;
            } else if ("Active".equals(filter)) {
                return !todo.getCompleted();
            } else if ("Completed".equals(filter)) {
                return todo.getCompleted();
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    public String getNewTask() {
        return newTask;
    }

    public void setNewTask(String newTask) {
        this.newTask = newTask;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        todoList = todoRepository.findAll();
    }

    public boolean getHasCompletedTodos(){
        return todoList.stream().anyMatch(Todo::getCompleted);
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
}
