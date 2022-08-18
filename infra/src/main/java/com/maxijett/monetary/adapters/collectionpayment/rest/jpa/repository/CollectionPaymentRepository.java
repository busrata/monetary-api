package com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionPaymentRepository extends JpaRepository<CollectionPaymentEntity, Long> {
}
