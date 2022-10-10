package com.sametcanal.service.abstracts;

import com.sametcanal.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {

    List<Role> getRoles();

    ResponseEntity<Role> getRoleById(Long id);

    // Create - Update - Delete
    Role createRole(Role role);

    ResponseEntity<Role> updateRole(Role role);

    Boolean deleteRole(Long id);

}
