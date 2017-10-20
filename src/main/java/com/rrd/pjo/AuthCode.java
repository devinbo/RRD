package com.rrd.pjo;

/**
 * Created by Administrator on 2017/10/18.
 */
public enum AuthCode {
    SUCCESS("1", "认证成功"), ERROR("2", "认证失败"), NOSTART("3", "尚未认证"), AUTHING("4", "认证中");

    String code;
    String msg;

    AuthCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
