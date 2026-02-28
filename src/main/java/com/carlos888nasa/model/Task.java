package com.carlos888nasa.model;
import com.carlos888nasa.exceptions.TaskValidationException;

import java.util.UUID;

public class Task {

    private String id;
    private String name;
    private Priority priority;
    private TaskStatus status;
    private int estimatedTime; // in minutes or seconds

    public Task(String name, Priority priority, int estimatedTime) {

        if (name == null || name.trim().isEmpty()) {
            throw new TaskValidationException("Task name cannot be null or empty");
        }

        if (estimatedTime < 0) {
            throw new TaskValidationException("Estimated time cannot be negative");
        }

        this.id = UUID.randomUUID().toString(); // Generate a unique ID for each task
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.estimatedTime = estimatedTime;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public void setEstimatedTime(int estimatedTime){
        this.estimatedTime = estimatedTime;
    }

}
