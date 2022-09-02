package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.CollectionPaymentDataAdapter;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT

@Sql(scripts = "classpath:sql/collection-payment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionPaymentDataAdapterTest extends AbstractIT {

    @Autowired
    CollectionPaymentDataAdapter collectionPaymentDataAdapter;

    @Autowired
    CollectionPaymentRepository collectionPaymentRepository;

    @Test
    public void shouldRetrieveCollectionPayment() {

        CollectionPayment response = collectionPaymentDataAdapter.retrieve(100L);
        assertNotNull(response);
        assertEquals(new BigDecimal("50.05"), response.getPos());
        assertEquals(200L, response.getStoreId());
        assertEquals(false, response.getIsDeleted());
    }

    @Test
    public void shouldUpdateCollectionPayment() {

        CollectionPayment response = collectionPaymentDataAdapter.update(100L);
        assertNotNull(response);
        assertEquals(true, response.getIsDeleted());

    }

    @Test
    public void shouldFailCollectionPaymentNotExistsWithId() {
        //Given
        Long wrongId = 1234567890L;

        //When & Then
        assertThatExceptionOfType(MonetaryApiBusinessException.class)
                .isThrownBy(() -> collectionPaymentDataAdapter.retrieve(wrongId))
                .withMessage("monetaryapi.collectionpayment.notFound");
    }

}
