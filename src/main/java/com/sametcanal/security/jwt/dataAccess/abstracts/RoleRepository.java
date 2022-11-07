package com.sametcanal.security.jwt.dataAccess.abstracts;

import com.sametcanal.security.jwt.entities.concretes.Role;
import com.sametcanal.security.jwt.entities.abstracts.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
