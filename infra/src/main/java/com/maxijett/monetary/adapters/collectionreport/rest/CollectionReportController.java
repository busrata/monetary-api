package com.maxijett.monetary.adapters.collectionreport.rest;

import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.useCase.DriverGetDailyBonus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection-report")
public class CollectionReportController {

    private final UseCaseHandler<DriverDailyBonus, DriverGetDailyBonus> handler;

    @GetMapping("/daily-bonus/{driverId}")
    public ResponseEntity<DriverDailyBonus> getDailyBonusByDriverAndGroupId(@PathVariable Long driverId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam boolean isRequestFromMobil) {
        log.info("REST request to get dailyBonus by driverId:{} with startDate:{} and endDate:{}", driverId, startDate, endDate);
        return new ResponseEntity<DriverDailyBonus>(handler.handle(toUseCase(driverId, startDate, endDate, isRequestFromMobil)),
                HttpStatus.OK);
    }

    private DriverGetDailyBonus toUseCase(Long driverId, LocalDate starDate, LocalDate endDate, Boolean isRequestFromMobil) {
        return DriverGetDailyBonus.builder().driverId(driverId).startDate(starDate).endDate(endDate).isRequestForMobil(isRequestFromMobil).build();
    }

}
