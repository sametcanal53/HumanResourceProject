package com.sametcanal.webApi.controllers;

import com.sametcanal.business.requests.ChangeDayOff;
import com.sametcanal.business.requests.create.CreateHumanResourceRequest;
import com.sametcanal.business.requests.update.UpdateHumanResourceRequest;
import com.sametcanal.entitites.concretes.HumanResource;
import com.sametcanal.business.abstracts.HumanResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/humanresources/")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class HumanResourceController {

    private final HumanResourceService humanResourceService;

    @GetMapping("/")
    List<HumanResource> getHumanResources() {
        return this.humanResourceService.getHumanResources();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HUMAN_RESOURCE','ADMIN')")
    ResponseEntity<HumanResource> getHumanResourceById(@PathVariable Long id) {
        return this.humanResourceService.getHumanResourceById(id);
    }

    // Create - Update - Delete
    @PostMapping("/")
    HumanResource createHumanResource(@RequestBody CreateHumanResourceRequest createHumanResourceRequest) {
        return this.humanResourceService.createHumanResource(createHumanResourceRequest);
    }

    @PatchMapping("/")
    ResponseEntity<HumanResource> updateHumanResource(@RequestBody UpdateHumanResourceRequest humanResourceRequest) {
        return this.humanResourceService.updateHumanResource(humanResourceRequest);
    }

    @DeleteMapping("/{id}")
    Boolean deleteHumanResource(@PathVariable Long id) {
        return this.humanResourceService.deleteHumanResource(id);
    }


    @PostMapping("/changeEmployeeDayOff")
    public ResponseEntity<?> changeEmployeeDayOff(@RequestBody ChangeDayOff changeDayOff) {
        return this.humanResourceService.changeEmployeeDayOff(changeDayOff);
    }
}
