package com.sametcanal.service.abstracts;

import com.sametcanal.controller.request.HumanResourceRequest;
import com.sametcanal.model.HumanResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HumanResourceService {
    // Human Resource
    // List - Find By ID
    List<HumanResource> getHumanResources();

    ResponseEntity<HumanResource> getHumanResourceById(Long id);

    // Create - Update - Delete
    HumanResource createHumanResource(HumanResourceRequest humanResourceRequest);

    ResponseEntity<HumanResource> updateHumanResource(HumanResourceRequest humanResourceRequest);

    Boolean deleteHumanResource(Long id);

}
