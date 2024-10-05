package com.pmt.project_management.auth;

import com.pmt.project_management.user.UserResponse;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private UserResponse user;
}
