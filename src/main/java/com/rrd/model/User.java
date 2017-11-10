package com.rrd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 */
public class User implements Serializable{

    private Long id;
    private String userno;
    private String loginname;
    private String password;
    private String username;
    private String cell;
    private String phone;
    private String email;
    private String qq;
    private String crtuser;
    private String crtdate;
    private String upduser;
    private String upddate;
    private String recsts;
    private Boolean adminflg;
    private String part;
    private String partname;
    private String part_job;
    private String partjobname;
    private String m_role_id;
    private String role_name;
    private String tzauth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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

    public Boolean getAdminflg() {
        return adminflg;
    }

    public void setAdminflg(Boolean adminflg) {
        this.adminflg = adminflg;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPart_job() {
        return part_job;
    }

    public void setPart_job(String part_job) {
        this.part_job = part_job;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }


    public String getPartjobname() {
        return partjobname;
    }

    public void setPartjobname(String partjobname) {
        this.partjobname = partjobname;
    }

    public String getM_role_id() {
        return m_role_id;
    }

    public void setM_role_id(String m_role_id) {
        this.m_role_id = m_role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getTzauth() {
        return tzauth;
    }

    public void setTzauth(String tzauth) {
        this.tzauth = tzauth;
    }
}
