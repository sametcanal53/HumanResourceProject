package com.sametcanal.business.concretes;

import com.sametcanal.business.requests.create.CreateHumanResourceRequest;
import com.sametcanal.business.requests.update.UpdateHumanResourceRequest;
import com.sametcanal.exception.HumanResourceException;
import com.sametcanal.entitites.concretes.HumanResource;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import com.sametcanal.business.abstracts.HumanResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HumanResourceManager implements HumanResourceService {

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
    public HumanResource createHumanResource(CreateHumanResourceRequest createHumanResourceRequest) {
        HumanResource humanResource = HumanResource
                .builder()
                .humanResourceName(createHumanResourceRequest.getHumanResourceName())
                .build();
        return this.humanResourceRepository.save(humanResource);
    }

    @Override
    public ResponseEntity<HumanResource> updateHumanResource(UpdateHumanResourceRequest updateHumanResourceRequest) {
        if (!humanResourceRepository.existsById(updateHumanResourceRequest.getId())) {
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        HumanResource updateHumanResource = humanResourceRepository.findById(updateHumanResourceRequest.getId()).orElse(null);
        updateHumanResource.setHumanResourceName(updateHumanResourceRequest.getHumanResourceName());
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
