package com.sametcanal.business.requests.update;

import com.sametcanal.business.requests.base.EmployeeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRequest extends EmployeeRequest {
    private Long id;
}
