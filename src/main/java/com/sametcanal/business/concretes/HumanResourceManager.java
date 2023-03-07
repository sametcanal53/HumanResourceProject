package com.sametcanal.business.concretes;

import com.sametcanal.business.requests.ChangeDayOff;
import com.sametcanal.business.requests.create.CreateHumanResourceRequest;
import com.sametcanal.business.requests.update.UpdateHumanResourceRequest;
import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.dataAccess.abstracts.EmployeeRepository;
import com.sametcanal.entitites.concretes.Employee;
import com.sametcanal.entitites.concretes.HumanResource;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import com.sametcanal.business.abstracts.HumanResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class HumanResourceManager implements HumanResourceService {

    @Autowired
    HumanResourceRepository humanResourceRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<HumanResource> getHumanResources() {
        log.info("List of Human Resource");
        return this.humanResourceRepository.findAll();
    }

    @Override
    public ResponseEntity<HumanResource> getHumanResourceById(Long id) {
        if (!humanResourceRepository.existsById(id)) {
            log.error("Human Resource Not Found! ");
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        log.info("Human Resource Id : " + id);
        return ResponseEntity.ok(this.humanResourceRepository.findById(id).orElse(null));
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
        if (!humanResourceRepository.existsById(updateHumanResourceRequest.getId())) {
            log.error("Human Resource Not Found! ");
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        HumanResource updateHumanResource = humanResourceRepository.findById(updateHumanResourceRequest.getId()).orElse(null);
        updateHumanResource.setHumanResourceName(updateHumanResourceRequest.getHumanResourceName());
        log.info("Human Resource was successfully updated.");
        this.humanResourceRepository.save(updateHumanResource);
        return ResponseEntity.ok(updateHumanResource);
    }

    @Override
    public Boolean deleteHumanResource(Long id) {
        if (!humanResourceRepository.existsById(id)) {
            log.error("Human Resource Not Found! ");
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
        log.info("Human Resource was successfully deleted.");
        this.humanResourceRepository.deleteById(id);
        return true;
    }

    @Override
    public ResponseEntity<?> updateEmployeeDayOff(ChangeDayOff changeDayOff) {
        if (!employeeRepository.existsById(changeDayOff.getEmployeeId())) {
            log.error("Employee Not Found! ");
            throw new HumanResourceException("HRP-2001", "Employee Not Found", HttpStatus.NOT_FOUND);
        }
        if (!humanResourceRepository.existsById(changeDayOff.getHumanResourceId())) {
            log.error("Human Resource Not Found! ");
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }

        Employee employee = this.employeeRepository.findById(changeDayOff.getEmployeeId()).orElse(null);
        HumanResource humanResource = this.humanResourceRepository.findById(changeDayOff.getHumanResourceId()).orElse(null);

        if(Objects.equals(employee.getHumanResourceId(), humanResource.getId())){
            employee.setDayOff(changeDayOff.getDayOff());
            log.info(employee.getName()+"'s day off has been changed to "+changeDayOff.getDayOff().name());
            this.employeeRepository.save(employee);
        }else{
            log.error("There is no such employee in human resources" +
                    "\nDay off cannot be changed");
            throw new HumanResourceException("HRP-2003", "There is no such employee in human resources. Day off cannot be changed", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(employee.getName()+"'s day off has been changed to "+changeDayOff.getDayOff().name());
    }
}
