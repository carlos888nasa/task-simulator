package com.carlos888nasa.ui;

import com.carlos888nasa.controller.TaskController;
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
