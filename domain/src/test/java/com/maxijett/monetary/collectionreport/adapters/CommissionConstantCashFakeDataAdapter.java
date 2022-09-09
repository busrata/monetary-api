package com.maxijett.monetary.collectionreport.adapters;

import com.maxijett.monetary.collectionreport.model.CommissionConstantCash;
import com.maxijett.monetary.collectionreport.port.CommissionConstantCashPort;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class CommissionConstantCashFakeDataAdapter implements CommissionConstantCashPort {

    @Override
    public List<CommissionConstantCash> getCommissionConstantCashListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId) {
        return List.of(CommissionConstantCash.builder()
                .rate(BigDecimal.valueOf(0.5))
                .startTime(ZonedDateTime.parse("2022-08-08T00:00:00.000Z"))
                .endTime(ZonedDateTime.parse("2022-08-08T00:00:00.000Z"))
                .build());
    }
}
