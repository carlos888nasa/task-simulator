package com.carlos888nasa.service;

import com.carlos888nasa.model.Task;
import java.util.List;

public interface TaskManager {
    void addTask(Task task);
    Task executeNextTask();
    void updateTask(String task);
    List<Task> getAllTasks();
}
