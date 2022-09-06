package com.maxijett.monetary.billingpayment.adapters;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BillingPaymentPortFakeDataAdapter implements BillingPaymentPort {
    public List<BillingPayment> billings = new ArrayList<>();

    @Override
    public BillingPayment create(BillingPaymentCreate useCase) {
        billings.add(BillingPayment.builder()
                .storeId(useCase.getStoreId())
                .clientId(useCase.getClientId())
                .amount(useCase.getAmount())
                .payloadType(useCase.getPayloadType())
                .payingAccount(useCase.getPayingAccount())
                .paymentType(useCase.getPaymentType())
                .isDeleted(false)
                .build());
        return billings.get(0);
    }

    @Override
    public BillingPayment create(BillingPaymentPrePaidCreate useCase) {
        billings.add(BillingPayment.builder()
                .storeId(useCase.getStoreId())
                .clientId(useCase.getClientId())
                .amount(useCase.getPrePaidBillingAmount())
                .payloadType(useCase.getPayloadType())
                .paymentType(useCase.getPaymentType())
                .build());
        return billings.get(0);
    }

    @Override
    public BillingPayment retrieve(Long id) {
       return BillingPayment.builder()
            .id(3L)
            .isDeleted(false)
            .paymentType(PaymentType.CASH)
            .storeId(30L)
            .amount(BigDecimal.TEN)
            .clientId(20L)
            .payingAccount("storeChainAdmin")
            .build();
    }

    @Override
    public BillingPayment update(Long id) {
        BillingPayment billingPayment = retrieve(id);
        billingPayment.setIsDeleted(true);
        billings.add(retrieve(id));
        return billingPayment;
    }

    @Override
    public List<BillingPayment> getAllByGroupIdAndCreateOn(Long groupId, ZonedDateTime startTime, ZonedDateTime endTime){
        billings.add(BillingPayment.builder()
                .id(4L)
                .isDeleted(false)
                .paymentType(PaymentType.CASH)
                .storeId(30L)
                .amount(BigDecimal.TEN)
                .clientId(20L)
                .payingAccount("storeChainAdmin")
                .createOn(ZonedDateTime.parse("2022-09-02T05:00:00.000Z"))
                .groupId(groupId)
                .build());
        return billings;
    }

    @Override
    public List<BillingPayment> retrieveBillingPaymentMonthlyByStore(Long storeId, ZonedDateTime requestDate) {
        billings.add(BillingPayment.builder()
                .storeId(storeId)
                .amount(BigDecimal.valueOf(20.05))
                .build());
        billings.add(BillingPayment.builder()
                .storeId(storeId)
                .amount(BigDecimal.valueOf(30.05))
                .build());
        return billings;
    }

    @Override
    public List<BillingPayment> retrieveBillingPaymentListByDateBetweenAndStore(Long storeId, ZonedDateTime startDate, ZonedDateTime endDate){

        storeId = 111L;
        billings.add(BillingPayment.builder().storeId(storeId).paymentType(PaymentType.CASH).amount(BigDecimal.valueOf(50.05)).payloadType(PayloadType.NETTING).createOn(ZonedDateTime.now().minusDays(2L)).build());
        billings.add(BillingPayment.builder().storeId(storeId).paymentType(PaymentType.CASH).amount(BigDecimal.valueOf(55.05)).payloadType(PayloadType.NETTING).createOn(ZonedDateTime.now().minusDays(1L)).build());
        billings.add(BillingPayment.builder().storeId(storeId).paymentType(PaymentType.CREDIT_CARD).amount(BigDecimal.valueOf(50.05)).payloadType(PayloadType.NETTING).createOn(ZonedDateTime.now()).build());
        return billings;
    }

    public void assertContains(BillingPayment billingPayment) {
        assertThat(billings).containsAnyElementsOf(List.of(billingPayment));
    }


}
