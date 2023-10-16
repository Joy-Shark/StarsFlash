package com.huistar.huistarapps.UtilsEnty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnObject {
    private String code;
    private String message;
    private Object data;
}
