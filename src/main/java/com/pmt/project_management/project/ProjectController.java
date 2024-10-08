package com.pmt.project_management.project;

import com.pmt.project_management.common.PageResponse;
import com.pmt.project_management.user.User;
import com.pmt.project_management.user.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Integer> saveProject(@Valid @RequestBody ProjectRequest request, Authentication connectedUser) {
        return ResponseEntity.ok(projectService.save(request, connectedUser));
    }

    @GetMapping("/{project-id}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable("project-id") Integer projectId) {
        return ResponseEntity.ok(projectService.findById(projectId));
    }

    @GetMapping("/all-Projects")
    public ResponseEntity<PageResponse<ProjectResponse>> findAllProjects(@RequestParam(name = "page", defaultValue = "0", required = false) int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(projectService.findAllProjects(page, size));
    }


    @GetMapping("/owner")
    public ResponseEntity<PageResponse<ProjectResponse>> findAllProjectsByOwner(@RequestParam(name = "page", defaultValue = "0", required = false) int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(projectService.findAllProjectsByOwner(page, size, connectedUser));
    }

    @DeleteMapping("/{project-id}")
    public ResponseEntity<String> deleteProject(@PathVariable("project-id") Integer projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Le projet a été supprimé avec succès.");
    }

    @PostMapping("/{projectId}/invite")
    public ResponseEntity<ProjectResponse> inviteMemberToProject(@PathVariable Integer projectId,
                                                                 @RequestBody InviteMemberRequest request,
                                                                 Authentication authentication) {

        return ResponseEntity.ok(projectService.inviteMemberToProject(projectId, request, authentication));
    }

    @PostMapping("/{projectId}/assign-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> assignRoleToMember(@PathVariable Integer projectId,
                                                     @RequestBody AssignRoleRequest request,
                                                     Authentication authentication) {
        String assignedRole = projectService.assignRoleToMember(projectId, request, authentication);
        return ResponseEntity.ok("Rôle : " + assignedRole + " attribué avec succès !");
    }

    @PutMapping("/{projectId}/update-role")
    public ResponseEntity<String> updateMemberRole(@PathVariable Integer projectId,
                                                   @RequestBody AssignRoleRequest request,
                                                   Authentication authentication) {
        String updatedRole = projectService.updateMemberRole(projectId, request, authentication);
        return ResponseEntity.ok("Le rôle a été mis à jour : " + updatedRole);
    }


    @GetMapping("/{projectId}/member-details")
    public ResponseEntity<UserResponse> getMemberDetails(@PathVariable Integer projectId,
                                                         @RequestParam String email) {
        UserResponse memberDetails = projectService.getMemberDetails(projectId, email);
        return ResponseEntity.ok(memberDetails);
    }

    @GetMapping("/my-projects")
    public ResponseEntity<?> getMyProjects(Authentication authentication) {
        User connectedUser = (User) authentication.getPrincipal();
        List<ProjectResponse> projects = projectService.getProjectsForUser(connectedUser);

        // Vérifier si l'utilisateur n'a aucun projet assigné
        if (projects.isEmpty()) {
            return ResponseEntity.ok("Vous n'êtes assigné à aucun projet.");
        }

        // Sinon, retourner la liste des projets
        return ResponseEntity.ok(projects);
    }

    // Endpoint pour obtenir la liste des membres d'un projet
    @GetMapping("/{projectId}/members")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public ResponseEntity<List<UserResponse>> getProjectMembers(
            @PathVariable Integer projectId,
            Authentication authentication) {
        List<UserResponse> members = projectService.getProjectMembers(projectId, authentication);
        return ResponseEntity.ok(members);
    }
}

