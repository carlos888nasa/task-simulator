package com.carlos888nasa;

import com.carlos888nasa.controller.TaskController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Task Manager!");
        System.out.println("Available commands: add, execute, list, exit");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task priority (LOW, MEDIUM, HIGH): ");
                    String priority = scanner.nextLine();
                    System.out.print("Enter task duration (in minutes): ");
                    int duration = Integer.parseInt(scanner.nextLine());
                    taskController.addTask(description, priority, duration);
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
}