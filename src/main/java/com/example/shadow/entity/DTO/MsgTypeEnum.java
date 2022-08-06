package com.example.shadow.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgTypeEnum {
    PLAIN_TYPE(0),
    FILE_TYPE(1),
    PROJECT_TYPE(2),
    ASSIGN_TYPE(3);
    private final int code;
}
