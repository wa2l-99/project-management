package com.pmt.project_management.user;


import com.pmt.project_management.auth.RegistrationRequest;
import com.pmt.project_management.role.ERole;
import com.pmt.project_management.role.Role;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User toUser(RegistrationRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .id(request.getId())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .build();
    }

    public UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                // Vérifier que la liste des rôles n'est pas nulle avant la conversion
                .roles(user.getRoles() != null ?
                        user.getRoles().stream()
                                .map(role -> role.getNom().name())  // Conversion du Role en String (nom de l'ERole)
                                .collect(Collectors.toList())
                        : Collections.emptyList())  // Retourner une liste vide si aucun rôle
                .build();
    }
}
