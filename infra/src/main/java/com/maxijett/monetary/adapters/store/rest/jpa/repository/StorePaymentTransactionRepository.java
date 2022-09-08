package com.maxijett.monetary.adapters.store.rest.jpa.repository;

import com.maxijett.monetary.adapters.store.rest.jpa.entity.StorePaymentTransactionEntity;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorePaymentTransactionRepository extends JpaRepository<StorePaymentTransactionEntity, Long> {

    Optional<List<StorePaymentTransactionEntity>> findByOrderNumberAndEventTypeIn(String orderNumber, List<StoreEventType> eventTypes);

}
