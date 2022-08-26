package com.maxijett.monetary.adapters.driver;

import com.maxijett.monetary.adapters.driver.jpa.DriverCashDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
public class DriverCashController {

  private final DriverCashDataAdapter driverCashDataAdapter;

  @GetMapping("/{driverId}/amount")
  public ResponseEntity<DriverCash> getDriverCashAndPrepaidAmountByDriverIdAndGroupId(
      @PathVariable Long driverId,
      @RequestParam Long groupId) {
    log.info("Rest request to get driver amounts by driverId: {} and groupId: {}", driverId,
        groupId);
    DriverCash response = driverCashDataAdapter.retrieve(driverId, groupId);
    return ResponseEntity.ok(response);

  }
}
