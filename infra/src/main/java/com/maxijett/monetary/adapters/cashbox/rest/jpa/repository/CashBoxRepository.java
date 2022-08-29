package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.CashBoxEntity;
import com.maxijett.monetary.cashbox.model.CashBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBoxEntity, Long> {
    CashBoxEntity findByGroupId(Long id);

    List<CashBoxEntity> findAllByClientId(Long clientId);
}
