package com.sametcanal.business.requests;

import com.sametcanal.entitites.abstracts.DayOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeDayOff {
    private Long humanResourceId;
    private Long employeeId;
    private DayOff dayOff;
}
