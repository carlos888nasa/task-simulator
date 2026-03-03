package com.carlos888nasa.service;

import com.carlos888nasa.model.Priority;
import com.carlos888nasa.model.Task;
import com.carlos888nasa.model.TaskStatus;
import com.carlos888nasa.repository.TaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManagerImpl  implements TaskManager{

    private final List<Task> allTasks = new ArrayList<>();
    private final PriorityQueue<Task> executionQueue = new PriorityQueue<>(Comparator.comparing(Task::getPriority));
    private final TaskRepository taskRepository;

    public TaskManagerImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        List<Task> loadedTasks = taskRepository.loadTasks();
        allTasks.addAll(loadedTasks);
        cleanOldTasks();
        for(Task task : loadedTasks){
            if(task.getStatus() == TaskStatus.PENDING){
                executionQueue.offer(task);
            }
        }
    }

    @Override
    public void addTask(Task task) {
        allTasks.add(task);
        executionQueue.offer(task);
        System.out.println("[SERVICE] Task added: " + task.getName() + " with priority: " + task.getPriority());
        taskRepository.saveAll(allTasks);
    }

    @Override
    public void executeNextTask() {

        applyAging();
        Task task = executionQueue.poll();
        if (task != null) {
            task.setStatus(TaskStatus.IN_PROGRESS);
            System.out.println("[SERVICE] Executing task: " + task.getName() + " with priority: " + task.getPriority());
            taskRepository.saveAll(allTasks);
            return;
        }

        System.out.println("[SERVICE] No tasks to execute.");
    }

    @Override
    public void updateTask(String task, TaskStatus status) {
        applyAging();
        for(Task t : allTasks){
            if(t.getId().equals(task)){
                t.setStatus(status);
                if(status == TaskStatus.COMPLETED || status == TaskStatus.CANCELLED){
                    t.setResolvedAt(LocalDateTime.now());
                    executionQueue.remove(t);
                }

                System.out.println("[SERVICE] Task updated: " + t.getName() + " is now " + t.getStatus());
                taskRepository.saveAll(allTasks);
                return;
            }
        }
    }

    @Override
    public List<Task> getAllTasks() {
        applyAging();
        List<Task> sortedTasks = new ArrayList<>(allTasks);
        sortedTasks.sort(Comparator.comparing(Task::getPriority).reversed().thenComparing(Task::getCreatedAt));
        return sortedTasks;
    }

    @Override
    public void applyAging() {

        boolean updated = false;

        for (Task task : allTasks) {
            Duration time = Duration.between(task.getCreatedAt(), LocalDateTime.now());

            if (task.getStatus() == TaskStatus.PENDING) {
                if(time.toMinutes() >= 180 && task.getPriority() == Priority.LOW){

                    task.setPriority(Priority.MEDIUM);
                    executionQueue.remove(task);
                    executionQueue.offer(task);
                    updated = true;

                } else if(time.toMinutes() >= 360 && task.getPriority() == Priority.MEDIUM){

                    task.setPriority(Priority.HIGH);
                    executionQueue.remove(task);
                    executionQueue.offer(task);
                    updated = true;

                }
            }
        }

        if(updated){
            taskRepository.saveAll(allTasks);
        }

    }

    @Override
    public void cleanOldTasks() {
        boolean removed = allTasks.removeIf(task -> {
            if ((task.getStatus() == TaskStatus.COMPLETED || task.getStatus() == TaskStatus.CANCELLED) && task.getResolvedAt() != null) {
                Duration time = Duration.between(task.getResolvedAt(), LocalDateTime.now());
                return time.toDays() >= 30;
            }
            return false;
        });

        if(removed){
            taskRepository.saveAll(allTasks);
        }

    }
}
