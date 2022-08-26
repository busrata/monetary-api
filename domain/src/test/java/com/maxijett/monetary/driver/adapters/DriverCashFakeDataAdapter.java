package com.maxijett.monetary.driver.adapters;

import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.port.DriverCashPort;

import com.maxijett.monetary.driver.useCase.DriverCashListRetrieve;
import java.math.BigDecimal;
import java.sql.Driver;
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
    public DriverCash update(DriverCash driverCash, DriverPaymentTransaction driverPaymentTransaction) {
        cashes.add(driverCash);
        return driverCash;
    }

    public List<DriverCash> getListByGroupIdGreaterThanZero(Long groupId){
        return List.of(DriverCash.builder()
           .driverId(1L)
           .groupId(20L)
           .cash(BigDecimal.valueOf(12))
           .prepaidCollectionCash(BigDecimal.valueOf(23))
           .clientId(20000L)
           .build(),
           DriverCash.builder()
               .driverId(2L)
               .groupId(20L)
               .cash(BigDecimal.valueOf(18))
               .prepaidCollectionCash(BigDecimal.valueOf(32))
               .clientId(20000L)
               .build(),
           DriverCash.builder()
               .driverId(3L)
               .groupId(20L)
               .cash(BigDecimal.valueOf(15))
               .prepaidCollectionCash(BigDecimal.valueOf(24))
               .clientId(20000L)
               .build()
       );
    }

    public List<DriverCash> getListByClientIdGreaterThanZero(Long groupId){
        return List.of(DriverCash.builder()
                .driverId(1L)
                .groupId(20L)
                .cash(BigDecimal.valueOf(12))
                .prepaidCollectionCash(BigDecimal.valueOf(23))
                .clientId(20000L)
                .build(),
            DriverCash.builder()
                .driverId(2L)
                .groupId(20L)
                .cash(BigDecimal.valueOf(18))
                .prepaidCollectionCash(BigDecimal.valueOf(32))
                .clientId(20000L)
                .build(),
            DriverCash.builder()
                .driverId(3L)
                .groupId(15L)
                .cash(BigDecimal.valueOf(15.06))
                .prepaidCollectionCash(BigDecimal.valueOf(24))
                .clientId(20000L)
                .build(),
            DriverCash.builder()
                .driverId(4L)
                .groupId(20L)
                .cash(BigDecimal.valueOf(18))
                .prepaidCollectionCash(BigDecimal.valueOf(64))
                .clientId(20000L)
                .build(),
            DriverCash.builder()
                .driverId(4L)
                .groupId(18L)
                .cash(BigDecimal.valueOf(23))
                .prepaidCollectionCash(BigDecimal.valueOf(64))
                .clientId(20000L)
                .build()
        );
    }



    public void assertContains(DriverCash... driverCashes) {
        assertThat(cashes).containsAnyElementsOf(List.of(driverCashes));
    }
}
