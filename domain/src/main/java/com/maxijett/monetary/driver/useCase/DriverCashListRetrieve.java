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
public class DriverCashListRetrieve implements UseCase {

    private Long groupId;

    private Long clientId;

    public static DriverCashListRetrieve fromModel(Long groupId, Long clientId) {
        return DriverCashListRetrieve.builder().groupId(groupId).clientId(clientId).build();
    }

}
