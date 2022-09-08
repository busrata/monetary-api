package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverCashRepository extends JpaRepository<DriverCashEntity, Long> {
    Optional<DriverCashEntity> findByDriverIdAndGroupId(Long driverId, Long groupId);

    List<DriverCashEntity> findByGroupIdAndCashGreaterThan(Long groupId, BigDecimal value);

    List<DriverCashEntity> findByClientIdAndCashGreaterThan(Long clientId, BigDecimal value);
}
