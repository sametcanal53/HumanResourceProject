package com.sametcanal.controller.request;

import com.sametcanal.model.DayOff;
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
    private Long id;
    private String name;
    @Min(value = 0,message = "The value you entered cannot be less than 0.")
    private double salary;
    private DayOff dayOff;
    private Long humanResourceId;
}
