package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.adapters.CommissionConstantCashFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.CommissionConstantPosFakeDataAdapter;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.model.CommissionConstantAccrualValue;
import com.maxijett.monetary.collectionreport.port.CommissionConstantCashPort;
import com.maxijett.monetary.collectionreport.port.CommissionConstantPosPort;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.collectionreport.useCase.CommissionConstantByDateBetweenAndClientIdRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrieveCollectionReportCommissionConstantAccrualValueUseCaseHandlerTest {

    CommissionConstantCashPort commissionConstantCashPort = new CommissionConstantCashFakeDataAdapter();
    CommissionConstantPosPort commissionConstantPosPort = new CommissionConstantPosFakeDataAdapter();
    ShiftTimePort shiftTimePort = new ShiftTimeFakeDataAdapter();
    RetrieveCollectionReportCommissionConstantAccrualValueUseCaseHandler handler = new RetrieveCollectionReportCommissionConstantAccrualValueUseCaseHandler(commissionConstantCashPort, commissionConstantPosPort, shiftTimePort);

    @Test
    public void shouldBeRetrieveCommissionConstantAccrualValueByDateBetweenAndStore(){

        //Given
        CommissionConstantByDateBetweenAndClientIdRetrieve useCase = CommissionConstantByDateBetweenAndClientIdRetrieve.builder()
                .clientId(20L)
                .startDate(LocalDate.of(2022, 8, 7))
                .endDate(LocalDate.of(2022, 9 ,10))
                .build();

        //When
        CommissionConstantAccrualValue response = handler.handle(useCase);

        //Then
        assertEquals(BigDecimal.valueOf(0.5), response.getCommissionConstantCashList().get(0).getRate());
        assertEquals(BigDecimal.valueOf(0.5), response.getCommissionConstantPosList().get(0).getRate());


    }

}
