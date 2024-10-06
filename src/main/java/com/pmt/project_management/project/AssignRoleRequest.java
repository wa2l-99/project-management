package com.pmt.project_management.project;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {
    private String email;
    private String role;
}
