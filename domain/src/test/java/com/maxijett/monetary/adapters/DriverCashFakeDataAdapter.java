package com.maxijett.monetary.adapters;

import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.port.DriverCashPort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverCashFakeDataAdapter implements DriverCashPort {

    List<DriverCash> cashes = new ArrayList<>();

    @Override
    public DriverCash retrieve(Long driverId, Long groupId) {
        return DriverCash.builder()
                .id(1L)
                .driverId(2L)
                .clientId(20L)
                .groupId(1L)
                .prepaidCollectionCash(BigDecimal.valueOf(75))
                .cash(BigDecimal.valueOf(120))
                .build();
    }

    @Override
    public DriverCash update(DriverCash driverCash) {
        cashes.add(driverCash);
        return driverCash;
    }


    public void assertContains(DriverCash... driverCashes) {
        assertThat(cashes).containsAnyElementsOf(List.of(driverCashes));
    }
}
