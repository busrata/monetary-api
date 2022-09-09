package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentListGet;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBillingPaymentsByDateAndGroupIdUseCaseHandlerTest {

    BillingPaymentPort billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
    ShiftTimePort shiftTimePort = new ShiftTimeFakeDataAdapter();
    GetBillingPaymentsByDateAndGroupIdUseCaseHandler useCaseHandler = new GetBillingPaymentsByDateAndGroupIdUseCaseHandler(billingPaymentPort, shiftTimePort);

    @Test
    public void shouldBeReturnAllBillingPaymentByDateAndGroupId() {
        //Given
        BillingPaymentListGet useCase = BillingPaymentListGet.builder()
                .groupId(20L)
                .createOn(LocalDate.of(2022,9,2)).build();

        //When
        List<BillingPayment> billingPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertEquals(useCase.getGroupId(), billingPaymentList.get(0).getGroupId());

        assertThat(billingPaymentList.get(0).getCreateOn().toLocalDate()).isBetween(useCase.getCreateOn(), useCase.getCreateOn().plusDays(1L));

    }

}
