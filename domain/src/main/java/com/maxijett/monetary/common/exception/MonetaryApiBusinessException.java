package com.maxijett.monetary.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonetaryApiBusinessException extends RuntimeException {

    private final String key;
    private final String[] args;

    public MonetaryApiBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public MonetaryApiBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
