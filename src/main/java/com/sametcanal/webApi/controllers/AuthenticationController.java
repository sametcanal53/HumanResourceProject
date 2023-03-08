package com.sametcanal.webApi.controllers;

import com.sametcanal.business.rules.UserBusinessRules;
import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.entitites.concretes.User;
import com.sametcanal.security.jwt.business.concretes.UserDetail;
import com.sametcanal.security.jwt.business.requests.SignInRequest;
import com.sametcanal.security.jwt.business.requests.SignUpRequest;
import com.sametcanal.security.jwt.business.responses.JwtResponse;
import com.sametcanal.security.jwt.dataAccess.abstracts.RoleRepository;
import com.sametcanal.security.jwt.dataAccess.abstracts.UserRepository;
import com.sametcanal.security.jwt.entities.abstracts.ERole;
import com.sametcanal.security.jwt.entities.concretes.Role;
import com.sametcanal.security.jwt.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtUtils;
    private final UserBusinessRules userBusinessRules;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        this.userBusinessRules.checkIfUsernameExists(signUpRequest.getUsername());
        this.userBusinessRules.checkIfEmailExists(signUpRequest.getEmail());

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        user.setRoles(roleConfig(signUpRequest.getRole()));
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    public Set<Role> roleConfig(Set<String> inputRoles){
        Set<Role> roles = new HashSet<>();
        inputRoles.forEach(role -> {
            switch (role) {
                case "admin" -> {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> HumanResourceExceptionConstant.ROLE_NOT_FOUND);
                    roles.add(adminRole);
                }
                case "hr" -> {
                    Role humanResourceRole = roleRepository.findByName(ERole.ROLE_HUMAN_RESOURCE)
                            .orElseThrow(() -> HumanResourceExceptionConstant.ROLE_NOT_FOUND);
                    roles.add(humanResourceRole);
                }
                default -> {
                    Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                            .orElseThrow(() -> HumanResourceExceptionConstant.ROLE_NOT_FOUND);
                    roles.add(employeeRole);
                }
            }
        });
        return roles;
    }

}
