package org.severinu.demoapi.api.files;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum StatusType {

    STORED("STORED"),
    FAILED("FAILED"),
    QUEUED("QUEUED"),
    NOT_FOUND("NOT_FOUND");

    private String type;
}
