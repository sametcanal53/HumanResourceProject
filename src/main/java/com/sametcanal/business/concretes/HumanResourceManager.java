package com.sametcanal.business.concretes;

import com.sametcanal.business.requests.ChangeDayOff;
import com.sametcanal.business.requests.create.CreateHumanResourceRequest;
import com.sametcanal.business.requests.update.UpdateHumanResourceRequest;
import com.sametcanal.business.rules.EmployeeBusinessRules;
import com.sametcanal.business.rules.HumanResourceBusinessRoles;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.entitites.concretes.HumanResource;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import com.sametcanal.business.abstracts.HumanResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HumanResourceManager implements HumanResourceService {
    private final HumanResourceRepository humanResourceRepository;
    private final EmployeeRepository employeeRepository;
    private final HumanResourceBusinessRoles humanResourceBusinessRoles;
    private final EmployeeBusinessRules employeeBusinessRules;

    @Override
    public List<HumanResource> getHumanResources() {
        log.info("List of Human Resource");
        return this.humanResourceRepository.findAll();
    }

    @Override
    public ResponseEntity<HumanResource> getHumanResourceById(Long id) {
        this.humanResourceBusinessRoles.checkIfHumanResourceExists(id);

        log.info("Human Resource Id : " + id);
        return ResponseEntity.ok().body(this.humanResourceRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<HumanResource> createHumanResource(CreateHumanResourceRequest createHumanResourceRequest) {
        HumanResource humanResource = HumanResource
                .builder()
                .humanResourceName(createHumanResourceRequest.getHumanResourceName())
                .build();
        log.info("Human Resource was successfully created.");
        return ResponseEntity.ok().body(this.humanResourceRepository.save(humanResource));
    }

    @Override
    public ResponseEntity<HumanResource> updateHumanResource(UpdateHumanResourceRequest updateHumanResourceRequest) {
        this.humanResourceBusinessRoles.checkIfHumanResourceExists(updateHumanResourceRequest.getId());
        HumanResource updateHumanResource = humanResourceRepository.findById(updateHumanResourceRequest.getId()).orElse(null);
        updateHumanResource.setHumanResourceName(updateHumanResourceRequest.getHumanResourceName());
        log.info("Human Resource was successfully updated.");
        this.humanResourceRepository.save(updateHumanResource);
        return ResponseEntity.ok().body(updateHumanResource);
    }

    @Override
    public Boolean  deleteHumanResource(Long id) {
        this.humanResourceBusinessRoles.checkIfHumanResourceExists(id);
        log.info("Human Resource was successfully deleted.");
        this.humanResourceRepository.deleteById(id);
        return true;
    }

    @Override
    public ResponseEntity<?> changeEmployeeDayOff(ChangeDayOff changeDayOff) {
        this.employeeBusinessRules.checkIfEmployeeExists(changeDayOff.getEmployeeId());
        this.humanResourceBusinessRoles.checkIfHumanResourceExists(changeDayOff.getHumanResourceId());

        Employee employee = this.employeeRepository.findById(changeDayOff.getEmployeeId()).orElse(null);
        HumanResource humanResource = this.humanResourceRepository.findById(changeDayOff.getHumanResourceId()).orElse(null);

        this.humanResourceBusinessRoles.checkIfEmployeeAndHumanResourceMatch(humanResource,employee,changeDayOff);
        this.employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee.getName()+"'s day off has been changed to "+changeDayOff.getDayOff().name());
    }
}
