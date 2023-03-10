package com.sametcanal.security.jwt.business.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Data
public class SignUpRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    private Set<String> role;
    @NotBlank
    private String password;
}
