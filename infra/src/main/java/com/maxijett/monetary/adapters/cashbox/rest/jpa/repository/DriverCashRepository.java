package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverCashRepository extends JpaRepository<DriverCashEntity, Long> {
    DriverCashEntity findByDriverIdAndGroupId(Long driverId, Long groupId);
}