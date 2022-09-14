package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.DailyBonusValue;
import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.CollectionReportPort;
import com.maxijett.monetary.collectionreport.port.DailyBonusValuePort;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.useCase.DriverGetDailyBonus;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class DailyBonusGetUseCaseHandler implements
        UseCaseHandler<DriverDailyBonus, DriverGetDailyBonus> {


    private final CollectionReportPort collectionReportPort;

    private final ShiftTimePort shiftTimePort;

    private final DailyBonusValuePort dailyBonusValuePort;


    @Override
    public DriverDailyBonus handle(DriverGetDailyBonus useCase) {

        DailyBonusValue dailyBonusValue = dailyBonusValuePort.getDailyBonusValue();

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        HashMap<String, ZonedDateTime> editedTime = timeEditing(useCase, shiftTime);

        List<CollectionReport> noonShiftHotOrderList = collectionReportPort.getHotOrderListByPaymentDateRangeAndNoonShift(
                useCase.getDriverId(), editedTime.get("start"), editedTime.get("end"),
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        List<CollectionReport> noonShiftColdOrderList = collectionReportPort.getColdOrderListByPaymentDateAndNoonShift(
                useCase.getDriverId(), editedTime.get("start"), editedTime.get("end"),
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        List<CollectionReport> nightShiftOrderList = collectionReportPort.getListByPaymentDateAndNightShift(
                useCase.getDriverId(), editedTime.get("start"), editedTime.get("end"),
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        BigDecimal nightShiftDailyBonus = calculateNightShiftDailyBonus(nightShiftOrderList, dailyBonusValue, editedTime.get("start"), editedTime.get("end"));

        BigDecimal noonShiftHotOrderDailyBonus = calculateNoonShiftHotOrderDailyBonus(
                noonShiftHotOrderList, dailyBonusValue);

        BigDecimal noonShiftColdOrderDailyBonus = calculateNoonShiftColdOrderDailyBonus(
                noonShiftColdOrderList, dailyBonusValue);

        return DriverDailyBonus.builder()
                .NightShiftDailyBonus(nightShiftDailyBonus)
                .NoonShiftDailyBonus(noonShiftHotOrderDailyBonus.add(noonShiftColdOrderDailyBonus))
                .build();

    }

    private HashMap<String, ZonedDateTime> timeEditing(DriverGetDailyBonus useCase, ShiftTime shiftTime){
        ZonedDateTime dateTime = useCase.getStartDate().atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime zdtStart = dateTime.toLocalDate()
                .atTime(shiftTime.getNightShiftEndHour(), 0, 0).atZone(dateTime.getZone());
        ZonedDateTime zdtEnd;

        if (useCase.getEndDate() == null) {
            zdtEnd = dateTime.toLocalDate().plusDays(1)
                    .atTime(shiftTime.getNightShiftEndHour(), 0, 0).atZone(dateTime.getZone());
        } else {
            dateTime = useCase.getEndDate().atStartOfDay(ZoneId.systemDefault()).plusDays(1);
            zdtEnd = dateTime.toLocalDate().atTime(shiftTime.getNightShiftEndHour(), 0, 0)
                    .atZone(dateTime.getZone());
        }

        if (useCase.getIsRequestForMobil()) {
            zdtStart = zdtStart.minusDays(1);
            zdtEnd = zdtEnd.minusDays(1);
        }

        HashMap<String, ZonedDateTime> timeHashMap = new HashMap<>();
        timeHashMap.put("start", zdtStart);
        timeHashMap.put("end", zdtEnd);

        return timeHashMap;
    }

    private BigDecimal calculateNightShiftDailyBonus(List<CollectionReport> list, DailyBonusValue dailyBonusValue, ZonedDateTime start, ZonedDateTime end) {
        BigDecimal dailyBonus = BigDecimal.ZERO;

        long timeRange = start.until(end, ChronoUnit.DAYS);

        for(long i=0L; i<timeRange; i++) {


            ZonedDateTime finalStart = start;
            long dailyCount = list.stream().filter( cr -> cr.getPaymentDate().getDayOfMonth() == finalStart.getDayOfMonth()).count();

            if (dailyCount > 0 && dailyCount <= dailyBonusValue.getNightShiftSize()) {
                dailyBonus = dailyBonus.add(BigDecimal.valueOf(dailyBonusValue.getNightShiftMinValue()));
            }
            else if(dailyCount > dailyBonusValue.getNightShiftSize() )
            {
                dailyBonus = dailyBonus.add(BigDecimal.valueOf(dailyBonusValue.getNightShiftRate()).multiply(BigDecimal.valueOf(dailyCount)));
            }
            start = start.plusDays(1);
        }

        return dailyBonus;
    }

    private BigDecimal calculateNoonShiftHotOrderDailyBonus(List<CollectionReport> list, DailyBonusValue dailyBonusValue) {
        BigDecimal sumDistanceFee = list.stream().map(CollectionReport::getDistanceFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return sumDistanceFee.multiply(BigDecimal.valueOf(dailyBonusValue.getHotRate()));
    }

    private BigDecimal calculateNoonShiftColdOrderDailyBonus(List<CollectionReport> list, DailyBonusValue dailyBonusValue) {
        int sumDeliveryDistance = list.stream().map(CollectionReport::getDeliveryDistance)
                .reduce(0, Integer::sum);

        BigDecimal sumDeliveryDistanceValue = BigDecimal.valueOf(sumDeliveryDistance);

        BigDecimal opening = BigDecimal.valueOf(dailyBonusValue.getColdOpening()).multiply(BigDecimal.valueOf(list.size()));

        BigDecimal dailyBonus = sumDeliveryDistanceValue.multiply(BigDecimal.valueOf(dailyBonusValue.getColdRate())).add(opening)
                .divide(BigDecimal.valueOf(dailyBonusValue.getColdDivide()));

        return dailyBonus;
    }

}
