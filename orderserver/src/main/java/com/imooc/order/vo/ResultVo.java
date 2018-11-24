package com.imooc.order.vo;

import lombok.Data;

/**
 * Created by helei on 2018-11-15.
 */
@Data
public class ResultVo<T> {

    private Integer code;

    private String msg;

    private T data;
}
