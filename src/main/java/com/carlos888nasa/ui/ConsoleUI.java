package com.carlos888nasa.ui;

import com.carlos888nasa.controller.TaskController;
import com.carlos888nasa.exceptions.TaskValidationException;
import com.carlos888nasa.model.TaskStatus;

import java.util.Scanner;

public class ConsoleUI {
    private final TaskController taskController;
    private final Scanner scanner;

    public ConsoleUI() {
        this.taskController = new TaskController();
        this.scanner  = new Scanner(System.in);

    }

    public void start(){
        System.out.println("Welcome to the Task Manager!");
        System.out.println("Available commands: add, execute, list, update, help, exit");
        String command;

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    handleAddCommand();
                    break;
                case "execute":
                    taskController.executeNext();
                    break;
                case "list":
                    taskController.getAllTasks().forEach(task ->{
                        if(task.getStatus() != TaskStatus.COMPLETED &&  task.getStatus() != TaskStatus.CANCELLED){
                            System.out.println("[" + task.getId() + "] " + task.getName() + " - Priority: " + task.getPriority() + " - Status: " + task.getStatus());
                        }
                    });
                    break;

                case "update":
                    handleUpdateCommand();
                    break;

                case "help":
                    System.out.println("Available commands: add, execute, list, help, exit");
                    break;

                case "exit":
                    System.out.println("Exiting Task Manager. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }

    private void handleAddCommand() {

        try{
            System.out.print("Enter task description: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter task priority (LOW, MEDIUM, HIGH): ");
            String priority = scanner.nextLine().trim();

            System.out.print("Enter estimated time (in minutes): ");
            int duration = Integer.parseInt(scanner.nextLine().trim());

            taskController.addTask(description, priority, duration);

        }catch(NumberFormatException e){
        System.out.println("[UI ERROR] Duration must be a number.");
        }catch (TaskValidationException e){
            System.out.println("[LOGIC ERROR] " + e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("[UI ERROR] Invalid priority. Please enter LOW, MEDIUM, or HIGH.");
        }catch (Exception e){
            System.out.println("[UI ERROR] An unexpected error occurred. Please try again.");
        }
    }

    private void handleUpdateCommand() {
        System.out.print("Enter task ID to mark as completed: ");
        String taskId = scanner.nextLine().trim();
        if (taskId.contains("[")){
            taskId = taskId.substring(taskId.indexOf("[") + 1, taskId.indexOf("]"));
        }
        if(taskController.getTaskById(taskId) != null){
            String name = taskController.getTaskById(taskId).getName();
            System.out.println("What do you want to do with " + name + "? (complete, canceller)");
            String action = scanner.nextLine().trim().toLowerCase();
            if(action.equals("complete")){
                taskController.updateTask(taskId, String.valueOf(TaskStatus.COMPLETED));
                System.out.println("Task " + name + " marked as COMPLETED.");
            }else if(action.equals("canceller")){
                // Implement cancel logic if needed
                taskController.updateTask(taskId, String.valueOf(TaskStatus.CANCELLED));
                System.out.println("Canceling task: " + name);
            }
        }else{
            System.out.println("Task ID not found. Please try again.");
        }
    }

}