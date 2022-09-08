package com.maxijett.monetary.adapters.billingpayment.rest.jpa;

import com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity.BillingPaymentEntity;
import com.maxijett.monetary.adapters.billingpayment.rest.jpa.repository.BillingPaymentRepository;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        entity.setIsDeleted(false);
        entity.setCreateOn(useCase.getCreateOn());
        entity.setGroupId(useCase.getGroupId());
        return billingPaymentRepository.save(entity).toModel();

    }

    @Override
    public BillingPayment retrieve(Long id) {
        return billingPaymentRepository.findById(id).map(BillingPaymentEntity::toModel)
                .orElseThrow(() -> new MonetaryApiBusinessException("monetaryapi.billingpayment.notFound"));
    }

    @Transactional
    @Override
    public BillingPayment update(Long id) {

        billingPaymentRepository.updateBillingPaymentIsDeleted(id);

        return retrieve(id);
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
        entity.setCreateOn(useCase.getCreateOn());
        entity.setGroupId(useCase.getGroupId());
        entity.setIsDeleted(false);

        return billingPaymentRepository.save(entity).toModel();
    }

    @Override
    public List<BillingPayment> getAllByGroupIdAndCreateOn(Long groupId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return billingPaymentRepository.findAllByGroupIdAndCreateOnBetween(groupId, startTime, endTime).stream().map(BillingPaymentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<BillingPayment> retrieveBillingPaymentMonthlyByStore(Long storeId, ZonedDateTime requestDate) {
        return billingPaymentRepository.getAllMonthlyByStore(storeId, requestDate.getYear(), requestDate.getMonthValue()).stream().map(BillingPaymentEntity::toModel).collect(Collectors.toList());
    }

}
