package com.pmt.project_management.project;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberResponse {
    private String nom;
    private String email;
    private String role;
}
