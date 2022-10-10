package com.sametcanal.controller.api;

import com.sametcanal.model.Role;
import com.sametcanal.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor

public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    @GetMapping("/{id}")
    ResponseEntity<Role> getRoleById(@PathVariable Long id){
        return this.roleService.getRoleById(id);
    }

    @PostMapping("/")
    public Role createRole(@RequestBody Role role) {
        return this.roleService.createRole(role);
    }

    @PatchMapping("/")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        return this.roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteRole(@PathVariable Long id) {
        return this.roleService.deleteRole(id);
    }


}
