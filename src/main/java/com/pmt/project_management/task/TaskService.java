package com.pmt.project_management.task;

import com.pmt.project_management.project.Project;
import com.pmt.project_management.project.ProjectRepository;
import com.pmt.project_management.user.User;
import com.pmt.project_management.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(Integer projectId, TaskRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Récupérer le projet par ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        // Log pour voir si l'utilisateur est bien dans les membres du projet
        System.out.println("Membres du projet : " + project.getMembers());
        System.out.println("Utilisateur connecté : " + user.getFullName());

        // Vérifier si l'utilisateur est membre ou administrateur du projet
        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre ou administrateur pour créer une tâche dans ce projet.");
        }

        // Mapper la requête en une entité Task
        Task task = taskMapper.toTaskRequest(request, project);

        // Sauvegarder la tâche dans la base de données
        taskRepository.save(task);

        // Retourner la tâche sous forme de TaskResponse
        return taskMapper.toTaskResponse(task);
    }


    // Méthode pour modifier une tâche
    public TaskResponse updateTask(Integer taskId, TaskRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Récupérer la tâche par ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + taskId));

        // Vérifier si l'utilisateur est membre ou administrateur du projet associé à la tâche
        Project project = task.getProject();
        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre ou administrateur pour modifier cette tâche.");
        }

        // Mettre à jour les informations de la tâche
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());

        taskRepository.save(task);

        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse getTaskById(Integer taskId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + taskId));

        Project project = task.getProject();
        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre, administrateur ou observateur pour voir cette tâche.");
        }

        return taskMapper.toTaskResponse(task);
    }


    public void deleteTask(Integer taskId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + taskId));

        Project project = task.getProject();
        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre ou administrateur pour supprimer cette tâche.");
        }
        taskRepository.delete(task);
    }


    public List<TaskResponse> getTasksByStatus(Integer projectId, EStatus status, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre, administrateur ou observateur pour voir les tâches.");
        }

        List<Task> tasks = taskRepository.findByProjectAndStatus(project, status);

        return tasks.stream()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByPriority(Integer projectId, EPriority priority, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre, administrateur ou observateur pour voir les tâches.");
        }

        List<Task> tasks = taskRepository.findByProjectAndPriority(project, priority);

        return tasks.stream()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }


    public List<TaskResponse> getAllTasksByProject(Integer projectId, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        if (!project.getMembers().contains(user) && !project.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("Vous devez être membre, administrateur ou observateur pour voir les tâches.");
        }

        List<Task> tasks = taskRepository.findByProject(project);

        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();

        return taskResponses;
    }
}
