package com.maxijett.monetary.adapters.coshbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverPaymentTransactionRepository extends JpaRepository<DriverPaymentTransactionEntity, Long> {
}
