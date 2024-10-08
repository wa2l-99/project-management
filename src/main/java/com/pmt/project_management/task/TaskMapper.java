package com.pmt.project_management.task;

import com.pmt.project_management.project.Project;
import com.pmt.project_management.user.User;
import com.pmt.project_management.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;


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


    public TaskHistoryResponse toTaskHistoryResponse(Task oldTask, Task newTask) {
        // Récupérer l'utilisateur qui a fait la dernière modification
        User lastModifiedByUser = userRepository.findById(newTask.getLastModifiedBy())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé pour l'ID : " + newTask.getLastModifiedBy()));

        // Générer la description dynamique en fonction des modifications
        String historyDescription = generateHistoryDescription(oldTask, newTask);

        return TaskHistoryResponse.builder()
                .taskId(newTask.getId())
                .taskName(newTask.getName())
                .projectName(newTask.getProject().getName())
                .lastModifiedById(lastModifiedByUser.getId())
                .lastModifiedByName(lastModifiedByUser.getFullName())
                .lastModifiedDate(newTask.getLastModifiedDate())
                .modificationDescription(historyDescription)
                .build();
    }

    public String generateHistoryDescription(Task oldTask, Task newTask) {
        StringBuilder description = new StringBuilder("Modifications apportées : ");

        if (!oldTask.getName().equals(newTask.getName())) {
            description.append(String.format("Nom modifié de '%s' à '%s'. ", oldTask.getName(), newTask.getName()));
        }

        if (!oldTask.getDescription().equals(newTask.getDescription())) {
            description.append(String.format("Description modifiée de '%s' à '%s'. ", oldTask.getDescription(), newTask.getDescription()));
        }

        if (!oldTask.getDueDate().equals(newTask.getDueDate())) {
            description.append(String.format("Date d'échéance modifiée de '%s' à '%s'. ", oldTask.getDueDate(), newTask.getDueDate()));
        }

        if (oldTask.getPriority() != newTask.getPriority()) {
            description.append(String.format("Priorité modifiée de '%s' à '%s'. ", oldTask.getPriority(), newTask.getPriority()));
        }

        if (oldTask.getStatus() != newTask.getStatus()) {
            description.append(String.format("Statut modifié de '%s' à '%s'. ", oldTask.getStatus(), newTask.getStatus()));
        }

        if (description.toString().equals("Modifications apportées : ")) {
            description.append("Aucune modification détectée.");
        }

        return description.toString();
    }
}
