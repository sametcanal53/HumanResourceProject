package com.sametcanal.webApi.controllers;

import com.sametcanal.business.rules.UserBusinessRules;
import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.security.jwt.business.concretes.UserDetail;
import com.sametcanal.security.jwt.business.requests.SignInRequest;
import com.sametcanal.security.jwt.business.requests.SignUpRequest;
import com.sametcanal.security.jwt.business.responses.JwtResponse;
import com.sametcanal.security.jwt.dataAccess.abstracts.RoleRepository;
import com.sametcanal.security.jwt.dataAccess.abstracts.UserRepository;
import com.sametcanal.security.jwt.entities.abstracts.ERole;
import com.sametcanal.security.jwt.entities.concretes.Role;
import com.sametcanal.entitites.concretes.User;
import com.sametcanal.security.jwt.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenUtil jwtUtils;
    @Autowired
    private UserBusinessRules userBusinessRules;

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

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
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

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }
}
