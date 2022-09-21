package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.CashBoxDataAdapter;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.enumaration.CashBoxEventType;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.store.model.enumeration.RecordType;
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
@Sql(scripts = "classpath:sql/cash-box.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CashBoxDataAdapterTest extends AbstractIT {

    @Autowired
    CashBoxDataAdapter cashBoxDataAdapter;

    @Test
    public void shouldRetrieveCashBox() {

        CashBox response = cashBoxDataAdapter.retrieve(20L);

        Assertions.assertNotNull(response);
        assertEquals(20L, response.getGroupId());
    }


    @Test
    public void shouldNotFoundCashBoxWhenWrongGroupId() {
        //Given
        Long invalidGroupId = 123456789L;

        //When & Then
        assertThatExceptionOfType(MonetaryApiBusinessException.class)
                .isThrownBy(() -> cashBoxDataAdapter.retrieve(invalidGroupId))
                .withMessage("monetaryapi.cashbox.notFound");

    }


    @Test
    public void shouldUpdateCashBox() {

        CashBox cashBox = CashBox.builder()
                .cash(BigDecimal.valueOf(20))
                .clientId(20000L)
                .groupId(20L)
                .userId(1L)
                .recordType(RecordType.ACTIVE)
                .build();

        CashBoxTransaction cashBoxTransaction = CashBoxTransaction.builder()
                .cashBoxEventType(CashBoxEventType.DRIVER_PAY)
                .driverId(1L)
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .payingAccount("cashBox")
                .amount(BigDecimal.valueOf(20))
                .build();

        CashBox response = cashBoxDataAdapter.update(cashBox, cashBoxTransaction);

        assertEquals(cashBox.getCash(), response.getCash());
        assertEquals(cashBox.getGroupId(), response.getGroupId());
        assertEquals(cashBox.getClientId(), response.getClientId());
        assertEquals(cashBox.getUserId(), response.getUserId());
    }


}
