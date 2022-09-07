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
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class GetDailyBonusUseCaseHandler implements
        UseCaseHandler<DriverDailyBonus, DriverGetDailyBonus> {


    private final CollectionReportPort collectionReportPort;

    private final ShiftTimePort shiftTimePort;

    private final DailyBonusValuePort dailyBonusValuePort;


    @Override
    public DriverDailyBonus handle(DriverGetDailyBonus useCase) {

        DailyBonusValue dailyBonusValue = dailyBonusValuePort.getDailyBonusValue();

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

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

        List<CollectionReport> noonShiftHotOrderList = collectionReportPort.getHotOrderListByPaymentDateRangeAndNoonShift(
                useCase.getDriverId(), zdtStart, zdtEnd,
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        List<CollectionReport> noonShiftColdOrderList = collectionReportPort.getColdOrderListByPaymentDateAndNoonShift(
                useCase.getDriverId(), zdtStart, zdtEnd,
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        List<CollectionReport> nightShiftOrderList = collectionReportPort.getListByPaymentDateAndNightShift(
                useCase.getDriverId(), zdtStart, zdtEnd,
                shiftTime.getNightShiftStartHour(), shiftTime.getNightShiftStartMinute(),
                shiftTime.getNightShiftEndHour());

        BigDecimal nightShiftDailyBonus = calculateNightShiftDailyBonus(nightShiftOrderList,dailyBonusValue);
        BigDecimal noonShiftHotOrderDailyBonus = calculateNoonShiftHotOrderDailyBonus(
                noonShiftHotOrderList,dailyBonusValue);
        BigDecimal noonShiftColdOrderDailyBonus = calculateNoonShiftColdOrderDailyBonus(
                noonShiftColdOrderList,dailyBonusValue);

        return DriverDailyBonus.builder()
                .NightShiftDailyBonus(nightShiftDailyBonus)
                .NoonShiftDailyBonus(noonShiftHotOrderDailyBonus.add(noonShiftColdOrderDailyBonus))
                .build();

    }

    private BigDecimal calculateNightShiftDailyBonus(List<CollectionReport> list, DailyBonusValue dailyBonusValue) {
        BigDecimal dailyBonus;
        if (list.size() <= dailyBonusValue.getNightShiftSize()) {
            dailyBonus = BigDecimal.valueOf(dailyBonusValue.getNightShiftMinValue());
        } else {

            BigDecimal listSize = BigDecimal.valueOf(list.size());
            dailyBonus = BigDecimal.valueOf(dailyBonusValue.getNightShiftRate()).multiply(listSize);
        }
        return dailyBonus;
    }

    private BigDecimal calculateNoonShiftHotOrderDailyBonus(List<CollectionReport> list, DailyBonusValue dailyBonusValue) {
        BigDecimal sumDistanceFee = list.stream().map(CollectionReport::getDistanceFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return sumDistanceFee.multiply(BigDecimal.valueOf(dailyBonusValue.getHotRate()));
    }

    private BigDecimal calculateNoonShiftColdOrderDailyBonus(List<CollectionReport> list,DailyBonusValue dailyBonusValue) {
        int sumDeliveryDistance = list.stream().map(CollectionReport::getDeliveryDistance)
                .reduce(0, Integer::sum);

        BigDecimal sumDeliveryDistanceValue = BigDecimal.valueOf(sumDeliveryDistance);

        BigDecimal opening = BigDecimal.valueOf(dailyBonusValue.getColdOpening()).multiply(BigDecimal.valueOf(list.size()));

        BigDecimal dailyBonus = sumDeliveryDistanceValue.multiply(BigDecimal.valueOf(dailyBonusValue.getColdRate())).add(opening)
                .divide(BigDecimal.valueOf(dailyBonusValue.getColdDivide()));

        return dailyBonus;
    }

}
