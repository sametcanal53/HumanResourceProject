package com.sametcanal.security.jwt.business.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
