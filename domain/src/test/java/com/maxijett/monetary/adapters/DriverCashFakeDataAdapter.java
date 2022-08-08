package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.port.DriverCashPort;

import java.math.BigDecimal;
import java.time.Instant;

public class DriverCashFakeDataAdapter implements DriverCashPort {
    @Override
    public DriverCash retrieve(Long driverId) {
        return DriverCash.builder()
                .id(1L)
                .driverId(driverId)
                .amount(BigDecimal.valueOf(500))
                .clientId(20000L)
                .createOn(Instant.now())
                .groupId(20L)
                .storeId(2020L).build();
    }
}
