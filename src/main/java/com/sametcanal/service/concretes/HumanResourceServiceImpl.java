package com.sametcanal.service.concretes;

import com.sametcanal.controller.request.HumanResourceRequest;
import com.sametcanal.exception.HumanResourceException;
import com.sametcanal.model.HumanResource;
import com.sametcanal.repository.HumanResourceRepository;
import com.sametcanal.service.abstracts.HumanResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HumanResourceServiceImpl implements HumanResourceService {

    private final HumanResourceRepository humanResourceRepository;

    @Override
    public List<HumanResource> getHumanResources() {
        return this.humanResourceRepository.findAll();
    }

    @Override
    public ResponseEntity<HumanResource> getHumanResourceById(Long id) {
        if (!humanResourceRepository.existsById(id)) {
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(this.humanResourceRepository.findById(id).orElse(null));
    }

    @Override
    public HumanResource createHumanResource(HumanResourceRequest humanResourceRequest) {
        HumanResource humanResource = HumanResource
                .builder()
                .humanResourceName(humanResourceRequest.getHumanResourceName())
                .build();
        return this.humanResourceRepository.save(humanResource);
    }

    @Override
    public ResponseEntity<HumanResource> updateHumanResource(HumanResourceRequest humanResourceRequest) {
        if (!humanResourceRepository.existsById(humanResourceRequest.getId())) {
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        HumanResource updateHumanResource = humanResourceRepository.findById(humanResourceRequest.getId()).orElse(null);
        updateHumanResource.setHumanResourceName(humanResourceRequest.getHumanResourceName());
        this.humanResourceRepository.save(updateHumanResource);
        return ResponseEntity.ok(updateHumanResource);
    }

    @Override
    public Boolean deleteHumanResource(Long id) {
        if (!humanResourceRepository.existsById(id)) {
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        this.humanResourceRepository.deleteById(id);
        return true;
    }
}
