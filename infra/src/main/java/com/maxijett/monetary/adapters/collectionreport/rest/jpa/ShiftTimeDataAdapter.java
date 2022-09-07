package com.maxijett.monetary.adapters.collectionreport.rest.jpa;

import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShiftTimeDataAdapter implements ShiftTimePort {


  @Value("${shiftTime.nightShift.startHour}")
  private int nightShiftStartHour;

  @Value("${shiftTime.nightShift.endHour}")
  private int nightShiftEndHour;

  @Value("${shiftTime.nightShift.minute}")
  private int nightShiftMinute;

  @Value("${shiftTime.morningShift.startHour}")
  private int morningShiftStartHour;

  @Value("${shiftTime.morningShift.endHour}")
  private int morningShiftEndHour;


  @Override
  public ShiftTime getShiftTime() {
    return ShiftTime.builder()
        .morningShiftStartHour(morningShiftStartHour)
        .morningShiftEndHour(morningShiftEndHour)
        .nightShiftStartMinute(nightShiftMinute)
        .nightShiftStartHour(nightShiftStartHour)
        .nightShiftEndHour(nightShiftEndHour)
        .build();
  }
}
