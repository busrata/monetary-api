package com.maxijett.monetary.collectionreport.adapters;

import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import com.maxijett.monetary.collectionreport.port.CollectionReportPort;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class CollectionReportFakeDataAdapter implements CollectionReportPort {

    @Override
    public List<CollectionReport> getHotOrderListByPaymentDateRangeAndNoonShift(Long driverId,
                                                                                ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return List.of(
                CollectionReport.builder().driverId(1L).groupId(20L).distanceFee(BigDecimal.valueOf(15.74)).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.HOT).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).distanceFee(BigDecimal.valueOf(23.56)).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.HOT).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).distanceFee(BigDecimal.valueOf(25.48)).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.HOT).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).distanceFee(BigDecimal.valueOf(31.42)).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.HOT).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).distanceFee(BigDecimal.valueOf(18.65)).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.HOT).build());
    }

    public List<CollectionReport> getColdOrderListByPaymentDateAndNoonShift(Long driverId,
                                                                            ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return List.of(
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(800).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(1000).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(675).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(3254).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(1540).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).deliveryDistance(2450).paymentDate(ZonedDateTime.now()).warmthType(WarmthType.COLD).build()
        );
    }

    public List<CollectionReport> getListByPaymentDateAndNightShift(Long driverId,
                                                                    ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return List.of(
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build(),
                CollectionReport.builder().driverId(1L).groupId(20L).paymentDate(ZonedDateTime.now()).build()
        );
    }

    @Override
    public List<CollectionReport> getListDateRangeByStore(Long storeId, ZonedDateTime startDate, ZonedDateTime endDate) {
        return List.of(
                CollectionReport.builder().storeId(57L).clientId(20000L).cash(BigDecimal.TEN).pos(BigDecimal.ONE).build(),
                CollectionReport.builder().storeId(57L).clientId(20000L).cash(BigDecimal.ZERO).pos(BigDecimal.TEN).build()
        );
    }

}
