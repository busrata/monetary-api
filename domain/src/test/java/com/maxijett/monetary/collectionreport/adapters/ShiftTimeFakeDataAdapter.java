package com.maxijett.monetary.collectionreport.adapters;

import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;

public class ShiftTimeFakeDataAdapter implements ShiftTimePort {

    @Override
    public ShiftTime getShiftTime() {
        return ShiftTime.builder()
                .nightShiftStartHour(23)
                .nightShiftStartMinute(30)
                .nightShiftEndHour(3)
                .morningShiftStartHour(9)
                .morningShiftEndHour(11)
                .build();
    }
}
