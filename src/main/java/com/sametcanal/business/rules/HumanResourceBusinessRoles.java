package com.sametcanal.business.rules;

import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.core.utilities.exception.HumanResourceExceptionConstant;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HumanResourceBusinessRoles {
    private final HumanResourceRepository humanResourceRepository;

    public void checkIfHumanResourceExists(Long id){
        if(!this.humanResourceRepository.existsById(id)){
            log.error("Human Resource Not Found!");
            throw HumanResourceExceptionConstant.HUMAN_RESOURCE_NOT_FOUND;
        }
    }

    public void checkIfEmployeeAndHumanResourceMatch(){
        log.error("There is no such employee in human resources" +
                "\nDay off cannot be changed");
        throw new HumanResourceException("HRP-3002", "There is no such employee in human resources. Day off cannot be changed", HttpStatus.BAD_REQUEST);
    }
}
