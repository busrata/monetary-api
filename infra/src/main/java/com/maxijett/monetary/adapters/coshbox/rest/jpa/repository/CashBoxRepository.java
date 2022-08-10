package com.maxijett.monetary.adapters.coshbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.CashBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBoxEntity, Long> {
    CashBoxEntity findByGroupId(Long id);
}
