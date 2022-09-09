package com.maxijett.monetary.adapters.collectionreport.rest;

import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.CommissionConstantAccrualValue;
import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import com.maxijett.monetary.collectionreport.useCase.CollectionReportByDateBetweenAndStoreRetrieve;
import com.maxijett.monetary.collectionreport.useCase.CommissionConstantByDateBetweenAndClientIdRetrieve;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.useCase.DriverGetDailyBonus;

import java.time.LocalDate;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection-report")
public class CollectionReportController {

  private final UseCaseHandler<DriverDailyBonus, DriverGetDailyBonus> getDailyBonusUseCaseHandler;

  private final UseCaseHandler<List<CollectionReport>, CollectionReportByDateBetweenAndStoreRetrieve> collectionReportDateRangeByStoreRetrieveUseCaseHandler;

  private final UseCaseHandler<CommissionConstantAccrualValue, CommissionConstantByDateBetweenAndClientIdRetrieve> commissionConstantByDateBetweenAndClientIdRetrieveUseCaseHandler;

  @GetMapping("/daily-bonus/{driverId}")
  public ResponseEntity<DriverDailyBonus> getDailyBonusByDriverAndGroupId(@PathVariable Long driverId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam boolean isRequestFromMobil) {
    log.info("REST request to get dailyBonus by driverId:{} with startDate:{} and endDate:{}", driverId, startDate, endDate);
    return new ResponseEntity<DriverDailyBonus>(getDailyBonusUseCaseHandler.handle(toUseCase(driverId, startDate, endDate, isRequestFromMobil)),
            HttpStatus.OK);
  }

  private DriverGetDailyBonus toUseCase(Long driverId, LocalDate starDate, LocalDate endDate, Boolean isRequestFromMobil) {
    return DriverGetDailyBonus.builder().driverId(driverId).startDate(starDate).endDate(endDate).isRequestForMobil(isRequestFromMobil).build();
  }

  @GetMapping("/{storeId}/date-range")
  public ResponseEntity<List<CollectionReport>> getCollectionReportDateRangeByStore(@PathVariable Long storeId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    log.info("REST request to get getCollectionReportDateRangeByStore by storeId:{} with startDate:{} and endDate:{}", storeId, startDate, endDate);
    return new ResponseEntity<List<CollectionReport>>(collectionReportDateRangeByStoreRetrieveUseCaseHandler.handle(CollectionReportByDateBetweenAndStoreRetrieve.builder()
            .storeId(storeId)
            .startDate(startDate)
            .endDate(endDate)
            .build()), HttpStatus.OK);
  }

  @GetMapping("/accrual-value/{clientId}/date-range")
  public ResponseEntity<CommissionConstantAccrualValue> getCollectionReportAccrualValueByDateBetweenAndStore(@PathVariable Long clientId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    log.info("REST request to get getCollectionReportAccrualValueByDateBetweenAndStore by clientId:{} with startDate:{} and endDate:{}", clientId, startDate, endDate);

    return new ResponseEntity<CommissionConstantAccrualValue>(commissionConstantByDateBetweenAndClientIdRetrieveUseCaseHandler.handle(CommissionConstantByDateBetweenAndClientIdRetrieve.builder()
            .clientId(clientId)
            .startDate(startDate)
            .endDate(endDate)
            .build()), HttpStatus.OK);
  }
}
