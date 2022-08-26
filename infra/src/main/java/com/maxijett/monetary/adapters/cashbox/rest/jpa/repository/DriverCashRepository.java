package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverCashEntity;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DriverCashRepository extends JpaRepository<DriverCashEntity, Long> {
    DriverCashEntity findByDriverIdAndGroupId(Long driverId, Long groupId);

    List<DriverCashEntity> findByGroupIdAndCashGreaterThan(Long groupId, BigDecimal value);

    List<DriverCashEntity> findByClientIdAndCashGreaterThan(Long clientId, BigDecimal value);
}
