package com.sametcanal.controller.api;

import com.sametcanal.controller.request.HumanResourceRequest;
import com.sametcanal.model.HumanResource;
import com.sametcanal.service.HumanResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/humanresource/")
@RequiredArgsConstructor
public class HumanResourceController {

    private final HumanResourceService humanResourceService;

    @GetMapping("/")
    List<HumanResource> getHumanResources(){
        return this.humanResourceService.getHumanResources();
    }

    @GetMapping("/{id}")
    ResponseEntity<HumanResource> getHumanResourceById(@PathVariable Long id){
        return this.humanResourceService.getHumanResourceById(id);
    }

    // Create - Update - Delete
    @PostMapping("/")
    HumanResource createHumanResource(@RequestBody HumanResourceRequest humanResourceRequest){
        return this.humanResourceService.createHumanResource(humanResourceRequest);
    }

    @PatchMapping("/")
    ResponseEntity<HumanResource> updateHumanResource(@RequestBody HumanResourceRequest humanResourceRequest){
        return this.humanResourceService.updateHumanResource(humanResourceRequest);
    }

    @DeleteMapping("/{id}")
    Boolean deleteHumanResource(@PathVariable Long id){
        return this.humanResourceService.deleteHumanResource(id);
    }
}
