package com.pmt.project_management.config;

import com.pmt.project_management.role.ERole;
import com.pmt.project_management.role.Role;
import com.pmt.project_management.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        // Boucle sur les rôles définis dans l'énumération ERole
        for (ERole roleEnum : ERole.values()) {
            // Vérifie si le rôle existe dans la base de données
            if (!roleRepository.existsByNom(roleEnum)) {
                // Si le rôle n'existe pas, crée-le et l'ajoute
                Role role = new Role();
                role.setNom(roleEnum);
                roleRepository.save(role);
            }
        }
    }


}
