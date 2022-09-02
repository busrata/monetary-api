package com.maxijett.monetary.store.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCollectionRetrieve implements UseCase {

    private Long clientId;

    private Long groupId;

}
