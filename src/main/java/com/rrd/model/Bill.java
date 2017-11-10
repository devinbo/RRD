package com.rrd.model;

import java.math.BigDecimal;

/**
 * 账单信息
 * Created by xinghb on 2017/10/23.
 */
public class Bill {
    private Long id;
    private Long book_id;
    private String book_no;
    private String product; //产品名称
    private String period; //期限
    private String state; //费用状态， 1：尚未开始 2：已还清 3:待还
    private String statenm;
    private String targetdate; //代还日期
    private String realdate; //实际还款日
    private BigDecimal repayfee; //本期应还
    private BigDecimal basefee; //基本费用
    private BigDecimal expiryfee; //逾期费
    private BigDecimal servicefee; //服务费
    private Long custom_id;
    private String cusname;
    private String crtuser;
    private String crtdate;
    private String upduser;
    private String upddate;
    private String recsts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public Long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(Long custom_id) {
        this.custom_id = custom_id;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public BigDecimal getRepayfee() {
        return repayfee;
    }

    public void setRepayfee(BigDecimal repayfee) {
        this.repayfee = repayfee;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(String targetdate) {
        this.targetdate = targetdate;
    }

    public String getRealdate() {
        return realdate;
    }

    public void setRealdate(String realdate) {
        this.realdate = realdate;
    }

    public BigDecimal getBasefee() {
        return basefee;
    }

    public void setBasefee(BigDecimal basefee) {
        this.basefee = basefee;
    }

    public BigDecimal getExpiryfee() {
        return expiryfee;
    }

    public void setExpiryfee(BigDecimal expiryfee) {
        this.expiryfee = expiryfee;
    }

    public BigDecimal getServicefee() {
        return servicefee;
    }

    public void setServicefee(BigDecimal servicefee) {
        this.servicefee = servicefee;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public String getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(String crtdate) {
        this.crtdate = crtdate;
    }

    public String getUpduser() {
        return upduser;
    }

    public void setUpduser(String upduser) {
        this.upduser = upduser;
    }

    public String getUpddate() {
        return upddate;
    }

    public void setUpddate(String upddate) {
        this.upddate = upddate;
    }

    public String getRecsts() {
        return recsts;
    }

    public void setRecsts(String recsts) {
        this.recsts = recsts;
    }

    public String getStatenm() {
        return statenm;
    }

    public void setStatenm(String statenm) {
        this.statenm = statenm;
    }

    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
    }
}
