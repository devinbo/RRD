package com.rrd.pjo;

/**
 * Created by Administrator on 2017/10/12.
 */
public enum ResCode {
    SUCCESS(1), Error(0);
    int code = 0;

    ResCode(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
