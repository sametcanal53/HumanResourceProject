package com.sametcanal.security.jwt.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JwtResponse {
    private String token;
    private String username;
    private List<String> roles;

}
