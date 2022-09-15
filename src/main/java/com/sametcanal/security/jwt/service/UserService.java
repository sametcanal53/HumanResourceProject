package com.sametcanal.security.jwt.service;
import com.sametcanal.security.jwt.dto.UserDto;
import com.sametcanal.security.jwt.model.UserEntity;
import com.sametcanal.security.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //database böyle bir kullanıcı var mı?
        UserEntity user = (UserEntity) userRepository.findByUsername(username);

        if (user == null) {
            log.error("There is no such user");
            throw new UsernameNotFoundException("BThere is no such user " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    //Database Username ve Passwordu set
    //password: maskelenmiş ==> maskeyi kaldırmak bcryptEncoder
    public UserEntity save(UserDto user) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }
}
