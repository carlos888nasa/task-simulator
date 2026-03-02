package com.carlos888nasa.controller;

import com.carlos888nasa.model.TaskStatus;
import com.carlos888nasa.repository.JsonTaskRepository;
import com.carlos888nasa.service.TaskManager;
import com.carlos888nasa.service.TaskManagerImpl;
import com.carlos888nasa.model.Task;
import com.carlos888nasa.model.Priority;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TaskController {
    private final TaskManager taskManager;

    public TaskController() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonTaskRepository jsonTaskRepository = new JsonTaskRepository(objectMapper);
        this.taskManager = new TaskManagerImpl(jsonTaskRepository);
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

     public void updateTask(String taskId, String status) {
         taskManager.updateTask(taskId, TaskStatus.valueOf(status.toUpperCase()));

     }

     public Task getTaskById(String taskId) {
         for (Task task : taskManager.getAllTasks()) {
             if (task.getId().equals(taskId)) {
                 return task;
             }
         }
         return null; // or throw an exception if not found
     }
}
