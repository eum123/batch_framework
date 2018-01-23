package net.mj.camel.batch.common.constants;

import lombok.Getter;

public enum BatchConstants {
    TEMP("TEMP");

    @Getter
    private final String id;

    private BatchConstants(String id) {
        this.id = id;
    }


}
