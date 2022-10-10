package com.sametcanal.service.concretes;

import com.sametcanal.exception.HumanResourceException;
import com.sametcanal.model.Role;
import com.sametcanal.repository.RoleRepository;
import com.sametcanal.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public ResponseEntity<Role> getRoleById(Long id) {
        if(!roleRepository.existsById(id)){
            throw new HumanResourceException("HRP-2003", "Role Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(this.roleRepository.findById(id).orElse(null));
    }

    @Override
    public Role createRole(Role role) {
        Role newRole = Role.builder()
                            .roleName(role.getRoleName())
                            .build();

        return this.roleRepository.save(newRole);
    }

    @Override
    public ResponseEntity<Role> updateRole(Role role) {
        if(!roleRepository.existsById(role.getId())){
            throw new HumanResourceException("HRP-2003", "Role Not Found", HttpStatus.NOT_FOUND);
        }
        Role updateRole = roleRepository.findById(role.getId()).orElse(null);
        updateRole.setRoleName(role.getRoleName());
        this.roleRepository.save(updateRole);
        return ResponseEntity.ok(updateRole);
    }

    @Override
    public Boolean deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new HumanResourceException("HRP-2003", "Role Not Found", HttpStatus.NOT_FOUND);
        }
        this.roleRepository.deleteById(id);
        return true;
    }
}
