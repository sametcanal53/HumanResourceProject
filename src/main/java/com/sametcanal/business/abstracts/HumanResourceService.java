package com.sametcanal.business.abstracts;

import com.sametcanal.business.requests.ChangeDayOff;
import com.sametcanal.business.requests.create.CreateHumanResourceRequest;
import com.sametcanal.business.requests.update.UpdateHumanResourceRequest;
import com.sametcanal.entitites.concretes.HumanResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HumanResourceService {
    // Human Resource
    // List - Find By ID
    List<HumanResource> getHumanResources();

    ResponseEntity<HumanResource> getHumanResourceById(Long id);

    // Create - Update - Delete
    ResponseEntity<HumanResource>  createHumanResource(CreateHumanResourceRequest createHumanResourceRequest);

    ResponseEntity<HumanResource> updateHumanResource(UpdateHumanResourceRequest updateHumanResourceRequest);

    Boolean  deleteHumanResource(Long id);

    // Employee Management
    ResponseEntity<?> changeEmployeeDayOff(ChangeDayOff changeDayOff);
    
}
