package com.maxijett.monetary.collectionreport.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShiftTime {

    private int nightShiftStartHour;

    private int nightShiftEndHour;

    private int nightShiftStartMinute;

    private int morningShiftStartHour;

    private int morningShiftEndHour;

}
