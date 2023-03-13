package com.sametcanal.business.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeSalary {
    private Long humanResourceId;
    private Long employeeId;
    private double salary;
}
