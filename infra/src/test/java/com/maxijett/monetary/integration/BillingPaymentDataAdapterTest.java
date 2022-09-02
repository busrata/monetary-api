package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.billingpayment.rest.jpa.BillingPaymentDataAdapter;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IT
public class BillingPaymentDataAdapterTest extends AbstractIT {

    @Autowired
    BillingPaymentDataAdapter billingPaymentDataAdapter;

    @Test
    public void shouldNotFoundWhenBillingPaymentNotExistsWithId() {

        //Given
        Long wrongBillingPaymentId = 1234567890L;

        //When & Then
        assertThatExceptionOfType(MonetaryApiBusinessException.class)
                .isThrownBy(() -> billingPaymentDataAdapter.retrieve(wrongBillingPaymentId))
                .withMessage("monetaryapi.billingpayment.notFound");
    }
}
