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
public class CollectedCashRetrieve implements UseCase {
    private Long driverId;
    private Long groupId;
}
