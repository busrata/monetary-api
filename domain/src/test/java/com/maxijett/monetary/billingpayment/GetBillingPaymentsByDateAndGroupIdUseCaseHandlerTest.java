package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentListGet;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBillingPaymentsByDateAndGroupIdUseCaseHandlerTest {

    BillingPaymentPort billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
    GetBillingPaymentsByDateAndGroupIdUseCaseHandler useCaseHandler = new GetBillingPaymentsByDateAndGroupIdUseCaseHandler(billingPaymentPort);

    @Test
    public void shouldBeReturnAllBillingPaymentByDateAndGroupId(){
        //Given
        BillingPaymentListGet useCase = BillingPaymentListGet.builder()
                .groupId(20L)
                .createOn(ZonedDateTime.parse("2022-09-02T00:00:00.000Z")).build();

        //When
        List<BillingPayment> billingPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertEquals(useCase.getGroupId(), billingPaymentList.get(0).getGroupId());

        assertThat(billingPaymentList.get(0).getCreateOn()).isBetween(useCase.getCreateOn(),useCase.getCreateOn().plusDays(1L));

    }

}
