package com.maxijett.monetary.collectionreport.adapters;

import com.maxijett.monetary.collectionreport.model.CommissionConstantPos;
import com.maxijett.monetary.collectionreport.port.CommissionConstantPosPort;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class CommissionConstantPosFakeDataAdapter implements CommissionConstantPosPort {

    @Override
    public List<CommissionConstantPos> getCommissionConstantPosListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId) {
        return List.of(CommissionConstantPos.builder()
                .rate(BigDecimal.valueOf(0.5))
                .startTime(ZonedDateTime.parse("2022-08-08T00:00:00.000Z"))
                .endTime(ZonedDateTime.parse("2022-08-08T00:00:00.000Z"))
                .build());
    }
}