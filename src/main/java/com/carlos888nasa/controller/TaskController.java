package com.carlos888nasa.controller;

import com.carlos888nasa.service.TaskManager;
import com.carlos888nasa.service.TaskManagerImpl;
import com.carlos888nasa.model.Task;
import com.carlos888nasa.model.Priority;

import java.util.List;

public class TaskController {
    private final TaskManager taskManager;

    public TaskController() {
        this.taskManager = new TaskManagerImpl();
    }

    public void addTask(String description, String priority, int duration) {

        Priority p = Priority.valueOf(priority.toUpperCase());
        Task task = new Task(description, p, duration);
        taskManager.addTask(task);

    }

    public void executeNext() {
        taskManager.executeNextTask();
    }

     public List<Task> getAllTasks() {
         return taskManager.getAllTasks();
     }

}
