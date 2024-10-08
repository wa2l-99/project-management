package com.pmt.project_management.task;

import com.pmt.project_management.project.Project;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toTaskRequest(TaskRequest taskRequest, Project project) {

        if (taskRequest == null) {
            return null;
        }
        return Task.builder()
                .id(taskRequest.getId())
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .priority(taskRequest.getPriority())
                .status(EStatus.TODO)
                .project(project)
                .assignedTo(null)
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        }

        boolean isAssigned = task.getAssignedTo() != null;

        String assignedTo = isAssigned ? task.getAssignedTo().getFullName() + " (" + task.getAssignedTo().getEmail() + ")" : "tâche non affectée";

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .isAssigned(isAssigned)
                .assignedTo(assignedTo)
                .projectName(task.getProject().getName())
                .build();
    }
}
