package com.maxijett.monetary.common.usecase;

import com.maxijett.monetary.common.model.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
