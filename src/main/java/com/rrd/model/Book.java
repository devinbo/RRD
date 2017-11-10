package com.rrd.model;

import java.math.BigDecimal;

/**
 * Created by xinghb on 2017/10/23.
 */
public class Book {
    private Long book_id;
    private String book_no;
    private Long product_id;
    private String product_name;
    private Long custom_id;
    private String cusname;
    private String cusphone;
    private String book_state; //订单状态
    private String book_statenm;
    private String audit_state; //订单审核状态 1： 审核中 2：审核通过 3：审核拒绝
    private BigDecimal remainfee; //剩余应还
    private BigDecimal expiryfee; //逾期费
    private BigDecimal serverfee; //服务费
    private BigDecimal currrepay; //当前应还
    private String repaydate; //还款日
    private String provefile; //证明文件名
    private String start_date;
    private String audit_errmemo; //
    private String crtuser;
    private String crtdate;
    private String upduser;
    private String upddate;
    private String recsts;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(String audit_state) {
        this.audit_state = audit_state;
    }

    public String getAudit_errmemo() {
        return audit_errmemo;
    }

    public void setAudit_errmemo(String audit_errmemo) {
        this.audit_errmemo = audit_errmemo;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(Long custom_id) {
        this.custom_id = custom_id;
    }

    public BigDecimal getExpiryfee() {
        return expiryfee;
    }

    public void setExpiryfee(BigDecimal expiryfee) {
        this.expiryfee = expiryfee;
    }

    public BigDecimal getCurrrepay() {
        return currrepay;
    }

    public void setCurrrepay(BigDecimal currrepay) {
        this.currrepay = currrepay;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getCusphone() {
        return cusphone;
    }

    public void setCusphone(String cusphone) {
        this.cusphone = cusphone;
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

    public BigDecimal getRemainfee() {
        return remainfee;
    }

    public void setRemainfee(BigDecimal remainfee) {
        this.remainfee = remainfee;
    }

    public String getBook_state() {
        return book_state;
    }

    public void setBook_state(String book_state) {
        this.book_state = book_state;
    }

    public String getBook_statenm() {
        return book_statenm;
    }

    public void setBook_statenm(String book_statenm) {
        this.book_statenm = book_statenm;
    }

    public String getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(String repaydate) {
        this.repaydate = repaydate;
    }

    public BigDecimal getServerfee() {
        return serverfee;
    }

    public void setServerfee(BigDecimal serverfee) {
        this.serverfee = serverfee;
    }

    public String getProvefile() {
        return provefile;
    }

    public void setProvefile(String provefile) {
        this.provefile = provefile;
    }
}
