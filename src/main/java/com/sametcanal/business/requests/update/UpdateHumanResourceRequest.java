package com.sametcanal.business.requests.update;

import com.sametcanal.business.requests.base.HumanResourceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHumanResourceRequest extends HumanResourceRequest {
    private Long id;
}
