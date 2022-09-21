package com.maxijett.monetary.driver;

import com.maxijett.monetary.driver.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.useCase.CollectedCashRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class CollectedCashRetrieveByGroupsUseCaseHandlerTest {

    DriverPaymentTransactionFakeDataAdapter driverPaymentTransactionFakeDataAdapter;

    CollectedCashRetrieveByGroupsUseCaseHandler handler;

    @BeforeEach
    public void setUp() {
        driverPaymentTransactionFakeDataAdapter = new DriverPaymentTransactionFakeDataAdapter();
        handler = new CollectedCashRetrieveByGroupsUseCaseHandler(driverPaymentTransactionFakeDataAdapter);
    }

    @Test
    public void shouldBeReturnCollectedCashWhenDriverEventTypePACKAGE_DELIVEREDorSUPPORT_ACCEPTED() {
        //Given
        CollectedCashRetrieve collectedCashRetrieve = CollectedCashRetrieve.builder()
                .groupId(20L)
                .driverId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        //When
        List<DriverPaymentTransaction> actualPaymentTransactions = handler.handle(collectedCashRetrieve);

        //Then
        assertThat(actualPaymentTransactions).isNotNull().hasSize(2)
                .extracting("driverId", "groupId", "cash", "eventType")
                .containsExactlyInAnyOrder(
                        tuple(1L, 20L, BigDecimal.valueOf(100.05), DriverEventType.PACKAGE_DELIVERED),
                        tuple(1L, 20L, BigDecimal.valueOf(48.02), DriverEventType.SUPPORT_ACCEPTED)
                );

    }
}
