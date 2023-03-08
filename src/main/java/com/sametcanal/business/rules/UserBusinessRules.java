package com.sametcanal.business.rules;

import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.security.jwt.dataAccess.abstracts.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBusinessRules {

    private final UserRepository userRepository;

    public void checkIfEmailExists(String email){
        if (userRepository.existsByEmail(email)) {
            log.error("Email already exists");
            throw HumanResourceExceptionConstant.EMAIL_ALREADY_EXISTS;
        }
    }

    public void checkIfUsernameExists(String username){
        if (userRepository.existsByUsername(username)) {
            log.error("Username already exists");
            throw HumanResourceExceptionConstant.USERNAME_ALREADY_EXISTS;
        }
    }

    public void checkIfUserExists(Long id){
        if(!userRepository.existsById(id)){
            log.error("User Not Found!");
            throw HumanResourceExceptionConstant.USER_NOT_FOUND;
        }
    }
}
