package com.maxijett.monetary.driver.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverGetDailyBonus implements UseCase {

    private Long driverId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isRequestForMobil;

}
