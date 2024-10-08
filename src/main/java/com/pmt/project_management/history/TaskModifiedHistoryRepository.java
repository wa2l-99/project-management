package com.pmt.project_management.history;

import com.pmt.project_management.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskModifiedHistoryRepository extends JpaRepository<TaskModifiedHistory, Integer> {

    // Récupérer l'historique des modifications d'une tâche
    List<TaskModifiedHistory> findByTask(Task task);

    // Récupérer l'historique des modifications pour une liste de tâches
    List<TaskModifiedHistory> findByTaskIn(List<Task> tasks);
}
