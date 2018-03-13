package net.mj.camel.batch.common.constants;

import lombok.Getter;

public enum BatchResultConstants {
    DOING('0'),
    SUCCESS('1'),
    FAIL('9');

    @Getter
    private char resultCode;

    private BatchResultConstants(char code) {
        this.resultCode = code;
    }

}
