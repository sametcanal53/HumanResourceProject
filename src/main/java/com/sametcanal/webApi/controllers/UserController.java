package com.sametcanal.webApi.controllers;

import com.sametcanal.business.abstracts.UserService;
import com.sametcanal.entitites.concretes.User;
import com.sametcanal.security.jwt.business.requests.SignInRequest;
import com.sametcanal.security.jwt.business.requests.SignUpRequest;
import com.sametcanal.security.jwt.business.responses.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        return this.userService.authenticateUser(signInRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return this.userService.registerUser(signUpRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteUser(@PathVariable Long id) {
        return this.userService.deleteUser(id);
    }
}
