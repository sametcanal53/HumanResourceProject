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
import com.sametcanal.security.jwt.business.responses.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class HumanResourceManager implements HumanResourceService {
    @Autowired
    private HumanResourceRepository humanResourceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HumanResourceBusinessRoles humanResourceBusinessRoles;
    @Autowired
    private EmployeeBusinessRules employeeBusinessRules;

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
    public HumanResource createHumanResource(CreateHumanResourceRequest createHumanResourceRequest) {
        HumanResource humanResource = HumanResource
                .builder()
                .humanResourceName(createHumanResourceRequest.getHumanResourceName())
                .build();
        log.info("Human Resource was successfully created.");
        return this.humanResourceRepository.save(humanResource);
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
    public Boolean deleteHumanResource(Long id) {
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

        if(Objects.equals(employee.getHumanResourceId(), humanResource.getId())){
            employee.setDayOff(changeDayOff.getDayOff());
            log.info(employee.getName()+"'s day off has been changed to "+changeDayOff.getDayOff().name());
            this.employeeRepository.save(employee);
        }
        else{
            this.humanResourceBusinessRoles.checkIfEmployeeAndHumanResourceMatch();
        }

        return ResponseEntity.ok().body(employee.getName()+"'s day off has been changed to "+changeDayOff.getDayOff().name());
    }
}
