package com.maxijett.monetary.adapters.store.jpa.repository;

import com.maxijett.monetary.adapters.store.jpa.entity.StorePaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorePaymentTransactionRepository extends JpaRepository<StorePaymentTransactionEntity, Long> {
}
