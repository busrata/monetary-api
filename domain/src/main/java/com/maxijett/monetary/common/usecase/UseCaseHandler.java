package com.maxijett.monetary.common.usecase;

import com.maxijett.monetary.common.model.UseCase;

public interface UseCaseHandler<R, T extends UseCase> {
    R handle(T useCase);
}
