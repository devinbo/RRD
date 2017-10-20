package com.rrd.model;


import java.math.BigDecimal;

public class Product {

    private Long id;
    private String name;
    private String prod_describe;
    private BigDecimal price; //总价
    private String deadline; //总期限数
    private String deadunit; //期限单位
    private BigDecimal deadprice; //每期价格
    private String productimg; //产品图片 base64
    private String imgname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getProd_describe() {
        return prod_describe;
    }

    public void setProd_describe(String prod_describe) {
        this.prod_describe = prod_describe;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeadunit() {
        return deadunit;
    }

    public void setDeadunit(String deadunit) {
        this.deadunit = deadunit;
    }

    public BigDecimal getDeadprice() {
        return deadprice;
    }

    public void setDeadprice(BigDecimal deadprice) {
        this.deadprice = deadprice;
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

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }
}
