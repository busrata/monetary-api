package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentMonthlyByStoreRetrieve;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class RetrieveBillingPaymentMonthlyByStoreUseCaseHandlerTest {

    BillingPaymentPort billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
    RetrieveBillingPaymentMonthlyByStoreUseCaseHandler useCaseHandler = new RetrieveBillingPaymentMonthlyByStoreUseCaseHandler(billingPaymentPort);

    @Test
    public void shouldReturnBillingPaymentMonthlyByStore() {
        //Given
        BillingPaymentMonthlyByStoreRetrieve useCase = BillingPaymentMonthlyByStoreRetrieve.builder()
                .storeId(20L)
                .requestDate(LocalDate.now())
                .build();

        //When
        List<BillingPayment> billingPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertThat(billingPaymentList).isNotNull().hasSize(2)
                .extracting("amount", "storeId")
                .containsExactlyInAnyOrder(
                        tuple(BigDecimal.valueOf(20.05), 20L),
                        tuple(BigDecimal.valueOf(30.05), 20L)
                );
    }
}
