package com.maxijett.monetary.adapters.billingpayment.rest.jpa.repository;

import com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity.BillingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingPaymentRepository extends JpaRepository<BillingPaymentEntity, Long> {
}
