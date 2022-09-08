package com.maxijett.monetary.adapters.collectionreport.rest.jpa;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CollectionReportEntity;
import com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository.CollectionReportRepository;
import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import com.maxijett.monetary.collectionreport.port.CollectionReportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionReportDataAdapter implements CollectionReportPort {


    private final CollectionReportRepository collectionReportRepository;

    @Override
    public List<CollectionReport> getHotOrderListByPaymentDateRangeAndNoonShift(Long driverId, ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return collectionReportRepository.findByDriverIdAndPaymentDateBetweenAndWarmthTypeAndNoonShift(driverId, WarmthType.HOT, startDate, endDate, startHour, minute, endHour).stream().map(
                CollectionReportEntity::toModel).collect(Collectors.toList());
    }


    @Override
    public List<CollectionReport> getColdOrderListByPaymentDateAndNoonShift(Long driverId, ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return collectionReportRepository.findByDriverIdAndPaymentDateBetweenAndWarmthTypeAndNoonShift(driverId, WarmthType.COLD, startDate, endDate, startHour, minute, endHour).stream().map(
                CollectionReportEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<CollectionReport> getListByPaymentDateAndNightShift(Long driverId, ZonedDateTime startDate, ZonedDateTime endDate, int startHour, int minute, int endHour) {
        return collectionReportRepository.findByDriverIdAndPaymentDateBetweenNightShift(driverId, startDate, endDate, startHour, minute, endHour).stream().map(CollectionReportEntity::toModel).collect(
                Collectors.toList());
    }

    @Override
    public List<CollectionReport> getListDateRangeByStore(Long storeId, ZonedDateTime startDate, ZonedDateTime endDate) {
        return collectionReportRepository.findAllByStoreIdAndPaymentDateBetween(storeId, startDate, endDate).stream().map(CollectionReportEntity::toModel).collect(Collectors.toList());
    }

}
