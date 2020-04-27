package com.etoak.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonsResult {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAILED_CODE = 100;

    public static final String SUCCESS_MSG = "success";
    public static final String FAILED_MSG = "failed";
    private Integer code;

    private String msg;

}
