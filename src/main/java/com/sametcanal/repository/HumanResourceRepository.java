package com.sametcanal.repository;

import com.sametcanal.model.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanResourceRepository extends JpaRepository<HumanResource, Long> {
}
