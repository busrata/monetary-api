package com.maxijett.monetary.driver.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverCashRetrieve implements UseCase {

  private Long driverId;

  private Long groupId;

  public static DriverCashRetrieve fromModel(Long driverId, Long groupId) {
    return DriverCashRetrieve.builder().driverId(driverId).groupId(groupId).build();

  }
}