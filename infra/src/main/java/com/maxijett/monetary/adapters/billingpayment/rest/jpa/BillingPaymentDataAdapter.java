package com.maxijett.monetary.adapters.billingpayment.rest.jpa;

import com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity.BillingPaymentEntity;
import com.maxijett.monetary.adapters.billingpayment.rest.jpa.repository.BillingPaymentRepository;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillingPaymentDataAdapter implements BillingPaymentPort {

    private final BillingPaymentRepository billingPaymentRepository;

    @Override
    public BillingPayment create(BillingPaymentCreate useCase) {
        BillingPaymentEntity entity = new BillingPaymentEntity();

        entity.setAmount(useCase.getAmount());
        entity.setClientId(useCase.getClientId());
        entity.setPaymentType(useCase.getPaymentType());
        entity.setPayingAccount(useCase.getPayingAccount());
        entity.setStoreId(useCase.getStoreId());
        entity.setPayloadType(useCase.getPayloadType());

        return billingPaymentRepository.save(entity).toModel();

    }

    @Override
    public BillingPayment create(BillingPaymentPrePaidCreate useCase) {
        BillingPaymentEntity entity = new BillingPaymentEntity();

        entity.setAmount(useCase.getPrePaidBillingAmount());
        entity.setClientId(useCase.getClientId());
        entity.setPaymentType(useCase.getPaymentType());
        entity.setStoreId(useCase.getStoreId());
        entity.setPayloadType(useCase.getPayloadType());
        entity.setPayingAccount(useCase.getDriverId().toString());

        return billingPaymentRepository.save(entity).toModel();
    }

}
