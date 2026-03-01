package com.carlos888nasa.repository;

import com.carlos888nasa.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonTaskRepository implements TaskRepository{

    private final ObjectMapper objectMapper;
    private final File file = new File("tasks.json");

    public JsonTaskRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void saveAll(List<Task> tasks) {
        try {
            objectMapper.writeValue(file, tasks);
            System.out.println("[REPOSITORY] Tasks saved to JSON file.");
        } catch (Exception e) {
            System.err.println("[REPOSITORY] Error saving tasks: " + e.getMessage());
        }
    }


    @Override
    public List<Task> loadTasks() {
        try {
            if (file.exists()) {
                List<Task> loadedTasks = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class));
                System.out.println("[REPOSITORY] Tasks loaded from JSON file.");
                return loadedTasks;
            } else {
                System.out.println("[REPOSITORY] No existing task file found. Starting with an empty task list.");
            }
        } catch (Exception e) {
            System.err.println("[REPOSITORY] Error loading tasks: " + e.getMessage());
        }

        return new ArrayList<>(); // Return an empty list if loading fails or file doesn't exist
    }

}
