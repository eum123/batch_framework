package net.mj.camel.batch.common.constants;

import lombok.Getter;

public enum BatchConstants {
    BATCH_RESULT("BATCH_RESULT"),
    BATCH_ERROR_MESSAGE("BATCH_ERROR_MESSAGE");

    @Getter
    private final String id;

    private BatchConstants(String id) {
        this.id = id;
    }


}
