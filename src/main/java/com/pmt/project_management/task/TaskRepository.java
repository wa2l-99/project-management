package com.pmt.project_management.task;

import com.pmt.project_management.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProjectAndStatus(Project project, EStatus status);

    List<Task> findByProjectAndPriority(Project project, EPriority priority);

    List<Task> findByProject(Project project);

}
