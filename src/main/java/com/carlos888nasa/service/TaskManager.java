package com.carlos888nasa.service;

import com.carlos888nasa.model.Task;
import com.carlos888nasa.model.TaskStatus;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);
    Task executeNextTask();
    void updateTask(String task, TaskStatus status);
    List<Task> getAllTasks();
}
