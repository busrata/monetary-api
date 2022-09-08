package com.maxijett.monetary.integration;


import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.collectionreport.rest.jpa.CollectionReportDataAdapter;
import com.maxijett.monetary.adapters.collectionreport.rest.jpa.ShiftTimeDataAdapter;
import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@Sql(scripts = "classpath:sql/collection-report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionReportDataAdapterTest extends AbstractIT {

    @Autowired
    CollectionReportDataAdapter collectionReportDataAdapter;

    @Autowired
    ShiftTimeDataAdapter shiftTimeDataAdapter;


    @Test
    void shouldGetListWhenSelectOneDay() {
        //Given
        Long driverId = 1L;

        ZonedDateTime startDate = ZonedDateTime.parse("2022-08-01T03:00:00.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        ZonedDateTime endDate = ZonedDateTime.parse("2022-08-02T03:00:00.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        ShiftTime shiftTime = shiftTimeDataAdapter.getShiftTime();

        //When
        List<CollectionReport> responseNoonShiftHotList = collectionReportDataAdapter.getHotOrderListByPaymentDateRangeAndNoonShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        List<CollectionReport> responseNoonShiftColdList = collectionReportDataAdapter.getColdOrderListByPaymentDateAndNoonShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        List<CollectionReport> responseNightShift = collectionReportDataAdapter.getListByPaymentDateAndNightShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        //Then

        assertEquals(2, responseNoonShiftHotList.size());
        assertEquals(2, responseNoonShiftColdList.size());
        assertEquals(2, responseNightShift.size());

        assertEquals(1, responseNoonShiftHotList.get(0).getDriverId());
        assertEquals(1, responseNoonShiftHotList.get(0).getPaymentDate().getDayOfMonth());

    }

    @Test
    void shouldGetListSelectMultipleDay() {
        //Given
        Long driverId = 1L;

        ZonedDateTime startDate = ZonedDateTime.parse("2022-08-01T03:00:00.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        ZonedDateTime endDate = ZonedDateTime.parse("2022-08-03T03:00:00.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        ShiftTime shiftTime = shiftTimeDataAdapter.getShiftTime();

        //When
        List<CollectionReport> responseNoonShiftHotList = collectionReportDataAdapter.getHotOrderListByPaymentDateRangeAndNoonShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        List<CollectionReport> responseNoonShiftColdList = collectionReportDataAdapter.getColdOrderListByPaymentDateAndNoonShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        List<CollectionReport> responseNightShift = collectionReportDataAdapter.getListByPaymentDateAndNightShift(driverId, startDate, endDate, shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(), shiftTime.getNightShiftEndHour());

        //Then

        assertEquals(3, responseNoonShiftHotList.size());
        assertEquals(4, responseNoonShiftColdList.size());
        assertEquals(3, responseNightShift.size());

        assertEquals(1, responseNoonShiftHotList.get(0).getDriverId());
        assertEquals(1, responseNoonShiftHotList.get(0).getPaymentDate().getDayOfMonth());

    }

}
