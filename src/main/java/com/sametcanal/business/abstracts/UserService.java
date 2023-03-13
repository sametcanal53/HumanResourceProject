package com.sametcanal.business.abstracts;


import com.sametcanal.entitites.concretes.User;
import com.sametcanal.security.jwt.business.requests.SignInRequest;
import com.sametcanal.security.jwt.business.requests.SignUpRequest;
import com.sametcanal.security.jwt.business.responses.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    // User
    // List - Find By ID
    List<User> getUsers();

    ResponseEntity<User> getUserById(Long id);

    ResponseEntity<User> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest);

    // Delete

    Boolean deleteUser(Long id);
}
