package com.sametcanal.dataAccess.abstracts;

import com.sametcanal.entitites.concretes.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanResourceRepository extends JpaRepository<HumanResource, Long> {
}
