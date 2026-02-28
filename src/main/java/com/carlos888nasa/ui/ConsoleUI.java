package com.carlos888nasa.ui;

import com.carlos888nasa.controller.TaskController;
import com.carlos888nasa.exceptions.TaskValidationException;

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
        System.out.println("Available commands: add, execute, list, exit");
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
                    taskController.getAllTasks().forEach(task ->
                            System.out.println(task.getPriority() + " - " + task.getName() + " - " + task.getStatus())
                    );
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

}
