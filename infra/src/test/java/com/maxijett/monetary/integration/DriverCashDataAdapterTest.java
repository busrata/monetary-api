package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverCashRepository;
import com.maxijett.monetary.adapters.driver.rest.jpa.DriverCashDataAdapter;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT

@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DriverCashDataAdapterTest extends AbstractIT {

    @Autowired
    DriverCashRepository driverCashRepository;

    @Autowired
    DriverCashDataAdapter driverCashDataAdapter;

    @Test
    public void shouldRetrieveDriverCash() {

        DriverCash response = driverCashDataAdapter.retrieve(1L, 20L);

        Assertions.assertNotNull(response);
        assertEquals(1L, response.getDriverId());
        assertEquals(20L, response.getGroupId());
    }

    @Test
    public void shouldFailDriverCashWhenWrongDriverIdOrWrongGroupId() {
        //Given
        Long wrongDriverId = 1234567890L;
        Long wrongGroupId = 1234567890L;

        //When & Then
        assertThatExceptionOfType(MonetaryApiBusinessException.class)
                .isThrownBy(() -> driverCashDataAdapter.retrieve(wrongDriverId, wrongGroupId))
                .withMessage("monetaryapi.drivercash.notFound");
    }

    @Test
    public void shouldUpdateDriverCash() {

        DriverCash driverCash = DriverCash.builder()
                .driverId(1L)
                .groupId(20L)
                .clientId(20000L)
                .prepaidCollectionCash(BigDecimal.valueOf(50))
                .cash(BigDecimal.valueOf(112))
                .build();

        DriverPaymentTransaction driverPaymentTransaction = DriverPaymentTransaction.builder()
                .driverId(1L)
                .groupId(20L)
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .paymentCash(BigDecimal.valueOf(12))
                .eventType(DriverEventType.REFUND_OF_PAYMENT)
                .orderNumber("12345678")
                .clientId(20000L)
                .build();


        DriverCash response = driverCashDataAdapter.update(driverCash, driverPaymentTransaction);


        assertEquals(driverCash.getDriverId(), response.getDriverId());
        assertEquals(driverCash.getCash(), response.getCash());
        assertEquals(driverCash.getPrepaidCollectionCash(), response.getPrepaidCollectionCash());
        assertEquals(driverCash.getClientId(), response.getClientId());
        assertEquals(driverCash.getGroupId(), response.getGroupId());

    }

}
