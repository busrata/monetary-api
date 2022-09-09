package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentMonthlyByStoreRetrieve;
import com.maxijett.monetary.collectionreport.adapters.ShiftTimeFakeDataAdapter;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class RetrieveBillingPaymentMonthlyByStoreUseCaseHandlerTest {

    BillingPaymentPortFakeDataAdapter billingPaymentPort = new BillingPaymentPortFakeDataAdapter();

    ShiftTimeFakeDataAdapter shiftTimePort = new ShiftTimeFakeDataAdapter();

    RetrieveBillingPaymentMonthlyByStoreUseCaseHandler useCaseHandler = new RetrieveBillingPaymentMonthlyByStoreUseCaseHandler(billingPaymentPort, shiftTimePort);

    @Test
    public void shouldReturnBillingPaymentMonthlyByStore() {
        //Given
        BillingPaymentMonthlyByStoreRetrieve useCase = BillingPaymentMonthlyByStoreRetrieve.builder()
                .storeId(111L)
                .requestDate(LocalDate.now())
                .build();

        //When
        List<BillingPayment> billingPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertThat(billingPaymentList).isNotNull().hasSize(3)
                .extracting("amount", "storeId", "paymentType")
                .containsExactlyInAnyOrder(
                        tuple(BigDecimal.valueOf(50.05), useCase.getStoreId(), PaymentType.CASH),
                        tuple(BigDecimal.valueOf(50.05), useCase.getStoreId(), PaymentType.CREDIT_CARD),
                        tuple(BigDecimal.valueOf(55.05), useCase.getStoreId(), PaymentType.CASH)
                );
    }
}
