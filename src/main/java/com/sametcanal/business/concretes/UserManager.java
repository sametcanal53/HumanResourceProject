package com.sametcanal.business.concretes;

import com.sametcanal.business.abstracts.UserService;
import com.sametcanal.business.rules.UserBusinessRules;
import com.sametcanal.entitites.concretes.User;
import com.sametcanal.security.jwt.dataAccess.abstracts.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManager implements UserService{

    private final UserRepository userRepository;

    private final UserBusinessRules userBusinessRules;

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
    public Boolean deleteUser(Long id) {
        this.userBusinessRules.checkIfUserExists(id);
        log.info("User was successfully deleted.");
        this.userRepository.deleteById(id);
        return true;
    }
}
