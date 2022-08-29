package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.usecase.CashBoxAmountGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAmountFromCashBoxByClientIdOrGroupIdUseCaseHandlerTest {

    CashBoxFakeDataAdapter cashBoxDataAdapter;
    GetAmountFromCashBoxByClientIdOrGroupIdUseCaseHandler handler;

    @BeforeEach
    public void setUp() {
        cashBoxDataAdapter = new CashBoxFakeDataAdapter();
        handler = new GetAmountFromCashBoxByClientIdOrGroupIdUseCaseHandler(cashBoxDataAdapter);

    }

    @Test
    public void getCashAmountWithGroupId() {
        //Given
        CashBoxAmountGet cashBoxAmountGet = CashBoxAmountGet.builder()
                .groupId(20L)
                .build();

        //When
        CashBox actualResponse = handler.handle(cashBoxAmountGet);

        //Then
        assertEquals(20L, actualResponse.getGroupId());
        assertEquals(BigDecimal.valueOf(250), actualResponse.getCash());
    }

    @Test
    public void getCashAmountWithClientId() {
        //Given
        CashBoxAmountGet cashBoxAmountGet = CashBoxAmountGet.builder()
                .clientId(20000L)
                .build();

        //When
        CashBox actualResponse = handler.handle(cashBoxAmountGet);

        //Then
        assertEquals(20000L, actualResponse.getClientId());
        assertEquals(BigDecimal.valueOf(160), actualResponse.getCash());
    }

}
