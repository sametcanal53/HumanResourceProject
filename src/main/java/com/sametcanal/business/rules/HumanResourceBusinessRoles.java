package com.sametcanal.business.rules;

import com.sametcanal.core.utilities.exception.HumanResourceException;
import com.sametcanal.dataAccess.abstracts.HumanResourceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class HumanResourceBusinessRoles {
    private HumanResourceRepository humanResourceRepository;

    public void checkIfHumanResourceExists(Long id){
        if(!this.humanResourceRepository.existsById(id)){
            log.error("Human Resource Not Found! ");
            throw new HumanResourceException("HRP-2002", "Human Resource Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
