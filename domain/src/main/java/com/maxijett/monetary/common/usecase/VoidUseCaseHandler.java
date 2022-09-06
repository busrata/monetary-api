package com.maxijett.monetary.common.usecase;

import com.maxijett.monetary.common.model.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {
    void handle(T useCase);
}
