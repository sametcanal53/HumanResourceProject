package com.sametcanal.business.requests.base;

import com.sametcanal.entitites.abstracts.DayOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {
    private String name;
    @Min(value = 0, message = "The value you entered cannot be less than 0.")
    private double salary;
    private DayOff dayOff;
    private Long humanResourceId;
}
