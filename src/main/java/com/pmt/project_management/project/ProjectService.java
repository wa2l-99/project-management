package com.pmt.project_management.project;

import com.pmt.project_management.exception.AlreadyExistsException;
import com.pmt.project_management.role.ERole;
import com.pmt.project_management.role.Role;
import com.pmt.project_management.role.RoleRepository;
import com.pmt.project_management.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;

    public Integer save(ProjectRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Project project = projectMapper.toProject(request);

        project.setOwner(user);

        // Ajouter l'utilisateur créateur du projet en tant qu'administrateur
        Role adminRole = roleRepository.findByNom(ERole.ADMIN).orElseThrow(() -> new IllegalStateException("Error: Role ADMIN is not found."));

        // Ajouter l'utilisateur créateur en tant que membre du projet avec le rôle ADMIN
        user.getRoles().add(adminRole);  // Assigner le rôle ADMIN à l'utilisateur

        if (projectRepository.existsByName(project.getName())) {
            throw new AlreadyExistsException("Le projet avec le nom " + project.getName() + " existe déja");
        }
        // Sauvegarder le projet dans la base de données
        projectRepository.save(project);

        return project.getId();
    }
}
