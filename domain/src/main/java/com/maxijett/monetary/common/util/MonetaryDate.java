package com.maxijett.monetary.common.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class MonetaryDate {

    private static final int TOLERANCE_HOUR = 1;

    public static ZonedDateTime convertStartZonedDateTime(LocalDate startDate, int nightShiftEndHour) {
        return startDate.atTime(nightShiftEndHour + TOLERANCE_HOUR, 0, 0).atZone(ZoneOffset.UTC);

    }

    public static ZonedDateTime convertEndZonedDateTime(LocalDate startDate, int nightShiftEndHour) {
        return startDate.plusDays(1).atTime(nightShiftEndHour + TOLERANCE_HOUR, 0, 0).atZone(ZoneOffset.UTC);

    }

}
