package com.carlos888nasa.repository;

import com.carlos888nasa.model.Task;
import java.util.List;

public interface TaskRepository {

        void saveAll(List<Task> tasks);
        List<Task> loadTasks();
}
