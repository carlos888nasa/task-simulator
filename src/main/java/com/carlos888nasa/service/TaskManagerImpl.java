package com.carlos888nasa.service;

import com.carlos888nasa.model.Task;
import com.carlos888nasa.model.TaskStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManagerImpl  implements TaskManager{

    private final List<Task> allTasks = new ArrayList<>();
    private final PriorityQueue<Task> executionQueue = new PriorityQueue<>(Comparator.comparing(Task::getPriority));

    @Override
    public void addTask(Task task) {
        allTasks.add(task);
        executionQueue.offer(task);
        System.out.println("[SERVICE] Task added: " + task.getName() + " with priority: " + task.getPriority());
    }

    @Override
    public Task executeNextTask() {

        Task task = executionQueue.poll();
        if (task != null) {
            task.setStatus(TaskStatus.IN_PROGRESS);
            System.out.println("[SERVICE] Executing task: " + task.getName() + " with priority: " + task.getPriority());
            return task;
        }

        System.out.println("[SERVICE] No tasks to execute.");
        return null;
    }

    @Override
    public void updateTask(String task) {
        for(Task t : allTasks){
            if(t.getId().equals(task)){
                t.setStatus(TaskStatus.COMPLETED);
                System.out.println("[SERVICE] Task updated: " + t.getName() + " is now COMPLETED.");
                return;
            }
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(allTasks);
    }
}
