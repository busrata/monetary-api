package com.maxijett.monetary.adapters.coshbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.CashBoxTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBoxTransactionRepository extends JpaRepository<CashBoxTransactionEntity, Long> {
}
