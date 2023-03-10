package com.sametcanal.business.concretes;

import com.sametcanal.business.abstracts.UserService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtUtils;

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        this.userBusinessRules.checkIfUserExists(id);

        log.info("User Id : " + id);
        return ResponseEntity.ok().body(this.userRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<User> registerUser(SignUpRequest signUpRequest) {
        this.userBusinessRules.checkIfUsernameExists(signUpRequest.getUsername());
        this.userBusinessRules.checkIfEmailExists(signUpRequest.getEmail());

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                roleConfig(signUpRequest.getRole()));
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @Override
    public ResponseEntity<JwtResponse> authenticateUser(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @Override
    public Boolean deleteUser(Long id) {
        this.userBusinessRules.checkIfUserExists(id);
        log.info("User was successfully deleted.");
        this.userRepository.deleteById(id);
        return true;
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
