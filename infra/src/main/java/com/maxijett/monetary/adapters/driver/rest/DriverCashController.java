package com.maxijett.monetary.adapters.driver.rest;

import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.useCase.CollectedCashRetrieve;
import com.maxijett.monetary.driver.useCase.DriverCashRetrieve;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.useCase.DriverCashListRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver-cash")
public class DriverCashController {

  private final UseCaseHandler<List<DriverCash>, DriverCashListRetrieve> handler;

  private final UseCaseHandler<DriverCash, DriverCashRetrieve> getDriverAmountFromDriver;

  private final UseCaseHandler<List<DriverPaymentTransaction>, CollectedCashRetrieve> collectedCashRetrieveUseCaseHandler;

  @GetMapping("/instant-list")
  public ResponseEntity<List<DriverCash>> getInstantCashGreaterThanZeroByGroupId(
      @RequestParam(required = false) Long groupId, @RequestParam(required = false) Long clientId) {
    log.info("REST request to get instant cash greater than zero bu groupId: {} and clientId : {}",
        groupId, clientId);
    return new ResponseEntity<List<DriverCash>>(
        handler.handle(DriverCashListRetrieve.fromModel(groupId, clientId)),
        HttpStatus.OK);
  }

  @GetMapping("/{driverId}/amount")
  public ResponseEntity<DriverCash> getDriverCashAndPrepaidAmountByDriverIdAndGroupId(
      @PathVariable Long driverId,
      @RequestParam Long groupId) {
    log.info("Rest request to get driver amounts by driverId: {} and groupId: {}", driverId,
        groupId);

    DriverCash response = getDriverAmountFromDriver.handle(
        DriverCashRetrieve.fromModel(driverId, groupId));
    return ResponseEntity.ok(response);

  }

    @GetMapping("/{driverId}/collected")
    public ResponseEntity<List<DriverPaymentTransaction>> getCollectedDriverCash(@PathVariable Long driverId, @RequestParam Long groupId) {
        log.info("Rest request to get getCollectedDriverCash by driverId: {}, groupId: {}", driverId, groupId);
       return ResponseEntity.ok(collectedCashRetrieveUseCaseHandler.handle(CollectedCashRetrieve.builder().driverId(driverId).groupId(groupId).build())) ;
    }

}
