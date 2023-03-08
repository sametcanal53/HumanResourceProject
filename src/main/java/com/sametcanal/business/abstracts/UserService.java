package com.sametcanal.business.abstracts;


import com.sametcanal.entitites.concretes.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{
    // User
    // List - Find By ID
    List<User> getUsers();

    ResponseEntity<User> getUserById(Long id);

    // Delete
    
    Boolean deleteUser(Long id);
}
