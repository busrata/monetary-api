package com.maxijett.monetary.billingpayment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDateBetweenByStoreRetrieve;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import org.junit.jupiter.api.Test;

public class RetrieveBillingPaymentListByDateBetweenAndStoreUseCaseHandlerTest {

    BillingPaymentPort billingPaymentPort = new BillingPaymentPortFakeDataAdapter();

    ShiftTimePort shiftTimePort = new ShiftTimeFakeDataAdapter();

    RetrieveBillingPaymentListByDateBetweenAndStoreUseCaseHandler useCaseHandler = new RetrieveBillingPaymentListByDateBetweenAndStoreUseCaseHandler(billingPaymentPort, shiftTimePort);

    @Test
    public void shouldReturnBillingPaymentListByDateBetweenAndStore() {

        //Given
        BillingPaymentDateBetweenByStoreRetrieve useCase = BillingPaymentDateBetweenByStoreRetrieve.builder()
            .storeId(20L)
            .startDate(LocalDate.now().minusDays(5L))
            .endDate(LocalDate.now())
            .build();

        //When
        List<BillingPayment> billingPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertThat(billingPaymentList).isNotNull().hasSize(3)
            .extracting("amount", "paymentType", "storeId")
            .containsExactlyInAnyOrder(
                tuple(BigDecimal.valueOf(50.05), PaymentType.CASH, 111L),
                tuple(BigDecimal.valueOf(55.05), PaymentType.CASH,  111L),
                tuple(BigDecimal.valueOf(50.05), PaymentType.CREDIT_CARD, 111L)
            );
    }
}
