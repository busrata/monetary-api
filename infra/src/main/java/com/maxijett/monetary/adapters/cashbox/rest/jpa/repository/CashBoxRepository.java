package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.CashBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBoxEntity, Long> {

    Optional<CashBoxEntity> findByGroupId(Long id);

    List<CashBoxEntity> findAllByClientId(Long clientId);
}
