package com.maxijett.monetary.collectionreport.port;

import com.maxijett.monetary.collectionreport.model.CollectionReport;

import java.time.ZonedDateTime;
import java.util.List;

public interface CollectionReportPort {

    List<CollectionReport> getHotOrderListByPaymentDateRangeAndNoonShift(Long driverId,
                                                                         ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour);

    List<CollectionReport> getColdOrderListByPaymentDateAndNoonShift(Long driverId,
                                                                     ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour);

    List<CollectionReport> getListByPaymentDateAndNightShift(Long driverId,
                                                             ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour);

}
