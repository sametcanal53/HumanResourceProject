package com.sametcanal.security.jwt.repository;

import com.sametcanal.security.jwt.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
}
