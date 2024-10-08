package com.pmt.project_management.project;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InviteMemberRequest {
    @NotBlank(message = "L'email est obligatoire pour cherhcer un utilisateur")
    @Email(message = "L'email doit être valide")
    private String email;
}
