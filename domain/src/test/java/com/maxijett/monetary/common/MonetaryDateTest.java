package com.maxijett.monetary.common;

import com.maxijett.monetary.common.util.MonetaryDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class MonetaryDateTest {

    @Test
    public void convertStartZonedDateTime() {
        //Given
        LocalDate startDate = LocalDate.of(2022, 9, 8);
        int nightShiftEndHour = 3;

        //When
        ZonedDateTime convertedTime = MonetaryDate.convertStartZonedDateTime(startDate, nightShiftEndHour);

        //Then
        Assertions.assertEquals(4, convertedTime.getHour());
        Assertions.assertEquals(8, convertedTime.getDayOfMonth());
        Assertions.assertEquals(9, convertedTime.getMonthValue());
        Assertions.assertEquals(2022, convertedTime.getYear());
    }

    @Test
    public void convertEndZonedDateTime() {
        //Given
        LocalDate endDate = LocalDate.of(2022, 9, 8);
        int nightShiftEndHour = 3;

        //When
        ZonedDateTime convertedTime = MonetaryDate.convertEndZonedDateTime(endDate, nightShiftEndHour);

        //Then
        Assertions.assertEquals(4, convertedTime.getHour());
        Assertions.assertEquals(9, convertedTime.getDayOfMonth());
        Assertions.assertEquals(9, convertedTime.getMonthValue());
        Assertions.assertEquals(2022, convertedTime.getYear());
    }
}
