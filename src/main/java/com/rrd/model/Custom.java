package com.rrd.model;

import com.rrd.pjo.AuthCode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 */
public class Custom implements Serializable{

    private Long custom_id;
    private String phone;
    private String password;
    private String name;
    private String state; //状态， 1:已认证 2:认证失败 3:尚未认证 4:认证中
    private String statenm;
    private Integer age;
    private String sex;
    private String qq;
    private String email;
    private String isrealauth; // 是否实名认证  1:已认证 2:认证失败 3:尚未认证 4:认证中
    private String isphoneauth; //是否手机认证  1:已认证 2:认证失败 3:尚未认证 4:认证中
    private String iseducauth; //是否学历认证1:已认证 2:认证失败 3:尚未认证 4:认证中
    private String islinkauth; //是否联系人认证
    private String isworkauth; //是否工作认证
    private String isrealauthnm;
    private String isphoneauthnm;
    private String iseducauthnm;
    private String islinkauthnm;
    private String isworkauthnm;
    private String wechat; //微信账号
    private String alipay; //支付宝账号
    private String homecell; //家庭电话
    private String identity; //身份证件号
    private String idenaddress; //身份证件所在地址
    private String birthday;  //身份证出生年月
    private String placeaddress; //现居住地址
    private Boolean haswork;  //是否有工作

    /**学历信息**/
    private Long educ_id;
    private String  purpose; //最高学历
    private String  purposenm; //最高学历名
    private String  educationtype; //教育类型
    private String  schoolname; //院校
    private String  specialitiename; //专业名称
    private String  timeenrollment; //入校时间
    private String  dategraduation; //入校时间
    private String  graduationstatus; //入校时间

    /**在校生信息**/
    private String student; //学生id
    private String college; // 就读学校
    private String start_date;
    private String specialty;
    private String dorm;

    private String picture;

    private String school;
    private String loginoutdate;
    /**工作信息**/
//    private String eductype; //最高学籍
    private Long work_id;
    private String work_year; //工作年限
    private String work_yearnm;
    private String work_state; //工作状态
    private String work_statenm;
    private String currcompany;
    private String currwork_year;  //现单位工作年限
    private String currwork_yearnm;
    private String currwork_address; //单位地址
    private String currwork_cell;  //单位电话
    private String company_sort; //现单位的性质
    private String company_sortnm;
    private String job; //当前职业
    private String jobnm;
    private String income; //个人月收入
    private String incomenm;

    private String onelinkman; //联系人1 姓名
    private String onelinkrealation; //联系人1 与你的关系
    private String onelinkrealationnm;
    private String onelinkcell; //联系人1 电话
    private String twolinkman; //联系人2 姓名
    private String twolinkrealation; //联系人2 与你的关系
    private String twolinkrealationnm;
    private String twolinkcell; //联系人2 电话
    private String linkapplydate; //联系人申请认证时间

    private Long kf_id; //客服人员
    private String kf_name; //客服名称
    private String kf_wechat; //客服微信号
    private String source; //来源1：app 2:内部添加
    private String crtdate;
    private String crtuser;
    private String upduser;
    private String upddate;
    private String recsts;

    //创建人信息
    private String part; //c创建人所在部门
    private String part_job; //部门员工， 部门总监


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

    public Long getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(Long custom_id) {
        this.custom_id = custom_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        if ("1".equals(state)) {
            this.statenm = "已认证";
        } else if ("2".equals(state)) {
            this.statenm = "未认证";
        } else if("3".equals(state)) {
            this.statenm = "尚未认证";
        } else if("4".equals(state)) {
            this.statenm = "认证中";
        }
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatenm() {
        return statenm;
    }

    public void setStatenm(String statenm) {
        this.statenm = statenm;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCompany_sortnm() {
        return company_sortnm;
    }

    public void setCompany_sortnm(String company_sortnm) {
        this.company_sortnm = company_sortnm;
    }

    public String getHomecell() {
        return homecell;
    }

    public void setHomecell(String homecell) {
        this.homecell = homecell;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdenaddress() {
        return idenaddress;
    }

    public void setIdenaddress(String idenaddress) {
        this.idenaddress = idenaddress;
    }

    public String getPlaceaddress() {
        return placeaddress;
    }

    public void setPlaceaddress(String placeaddress) {
        this.placeaddress = placeaddress;
    }

    public String getWork_year() {
        return work_year;
    }

    public void setWork_year(String work_year) {
        this.work_year = work_year;
    }

    public String getWork_yearnm() {
        return work_yearnm;
    }

    public void setWork_yearnm(String work_yearnm) {
        this.work_yearnm = work_yearnm;
    }

    public String getWork_state() {
        return work_state;
    }

    public void setWork_state(String work_state) {
        this.work_state = work_state;
    }

    public String getWork_statenm() {
        return work_statenm;
    }

    public void setWork_statenm(String work_statenm) {
        this.work_statenm = work_statenm;
    }

    public String getCurrcompany() {
        return currcompany;
    }

    public void setCurrcompany(String currcompany) {
        this.currcompany = currcompany;
    }

    public String getCurrwork_year() {
        return currwork_year;
    }

    public void setCurrwork_year(String currwork_year) {
        this.currwork_year = currwork_year;
    }

    public String getCurrwork_yearnm() {
        return currwork_yearnm;
    }

    public void setCurrwork_yearnm(String currwork_yearnm) {
        this.currwork_yearnm = currwork_yearnm;
    }

    public String getCurrwork_address() {
        return currwork_address;
    }

    public void setCurrwork_address(String currwork_address) {
        this.currwork_address = currwork_address;
    }

    public String getCurrwork_cell() {
        return currwork_cell;
    }

    public void setCurrwork_cell(String currwork_cell) {
        this.currwork_cell = currwork_cell;
    }

    public String getCompany_sort() {
        return company_sort;
    }

    public void setCompany_sort(String company_sort) {
        this.company_sort = company_sort;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobnm() {
        return jobnm;
    }

    public void setJobnm(String jobnm) {
        this.jobnm = jobnm;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIncomenm() {
        return incomenm;
    }

    public void setIncomenm(String incomenm) {
        this.incomenm = incomenm;
    }

    public String getOnelinkman() {
        return onelinkman;
    }

    public void setOnelinkman(String onelinkman) {
        this.onelinkman = onelinkman;
    }


    public String getOnelinkcell() {
        return onelinkcell;
    }

    public void setOnelinkcell(String onelinkcell) {
        this.onelinkcell = onelinkcell;
    }

    public String getTwolinkman() {
        return twolinkman;
    }

    public void setTwolinkman(String twolinkman) {
        this.twolinkman = twolinkman;
    }

    public String getOnelinkrealation() {
        return onelinkrealation;
    }

    public void setOnelinkrealation(String onelinkrealation) {
        this.onelinkrealation = onelinkrealation;
    }

    public String getTwolinkrealation() {
        return twolinkrealation;
    }

    public void setTwolinkrealation(String twolinkrealation) {
        this.twolinkrealation = twolinkrealation;
    }

    public String getTwolinkcell() {
        return twolinkcell;
    }

    public void setTwolinkcell(String twolinkcell) {
        this.twolinkcell = twolinkcell;
    }

    public String getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(String crtdate) {
        this.crtdate = crtdate;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
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

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getOnelinkrealationnm() {
        return onelinkrealationnm;
    }

    public void setOnelinkrealationnm(String onelinkrealationnm) {
        this.onelinkrealationnm = onelinkrealationnm;
    }

    public String getTwolinkrealationnm() {
        return twolinkrealationnm;
    }

    public void setTwolinkrealationnm(String twolinkrealationnm) {
        this.twolinkrealationnm = twolinkrealationnm;
    }

    public String getIsrealauth() {
        return isrealauth;
    }

    public void setIsrealauth(String isrealauth) {
        if(isrealauth.equals(AuthCode.SUCCESS.getCode())) {
            this.isrealauthnm = AuthCode.SUCCESS.getMsg();
        }else if(isrealauth.equals(AuthCode.ERROR.getCode())) {
            this.isrealauthnm = AuthCode.ERROR.getMsg();
        }else if(isrealauth.equals(AuthCode.NOSTART.getCode())) {
            this.isrealauthnm = AuthCode.NOSTART.getMsg();
        }else if(isrealauth.equals(AuthCode.AUTHING.getCode())) {
            this.isrealauthnm = AuthCode.AUTHING.getMsg();
        }
        this.isrealauth = isrealauth;
    }

    public String getIsphoneauth() {
        return isphoneauth;
    }

    public void setIsphoneauth(String isphoneauth) {
        if(isphoneauth.equals(AuthCode.SUCCESS.getCode())) {
            this.isphoneauthnm = AuthCode.SUCCESS.getMsg();
        }else if(isphoneauth.equals(AuthCode.ERROR.getCode())) {
            this.isphoneauthnm = AuthCode.ERROR.getMsg();
        }else if(isphoneauth.equals(AuthCode.NOSTART.getCode())) {
            this.isphoneauthnm = AuthCode.NOSTART.getMsg();
        }else if(isphoneauth.equals(AuthCode.AUTHING.getCode())) {
            this.isphoneauthnm = AuthCode.AUTHING.getMsg();
        }
        this.isphoneauth = isphoneauth;
    }

    public String getIseducauth() {
        return iseducauth;
    }

    public void setIseducauth(String iseducauth) {
        if(iseducauth.equals(AuthCode.SUCCESS.getCode())) {
            this.iseducauthnm = AuthCode.SUCCESS.getMsg();
        }else if(iseducauth.equals(AuthCode.ERROR.getCode())) {
            this.iseducauthnm = AuthCode.ERROR.getMsg();
        }else if(iseducauth.equals(AuthCode.NOSTART.getCode())) {
            this.iseducauthnm = AuthCode.NOSTART.getMsg();
        }else if(iseducauth.equals(AuthCode.AUTHING.getCode())) {
            this.iseducauthnm = AuthCode.AUTHING.getMsg();
        }
        this.iseducauth = iseducauth;
    }

    public String getIslinkauth() {
        return islinkauth;
    }

    public void setIslinkauth(String islinkauth) {
        if(islinkauth.equals(AuthCode.SUCCESS.getCode())) {
            this.islinkauthnm = AuthCode.SUCCESS.getMsg();
        }else if(islinkauth.equals(AuthCode.ERROR.getCode())) {
            this.islinkauthnm = AuthCode.ERROR.getMsg();
        }else if(islinkauth.equals(AuthCode.NOSTART.getCode())) {
            this.islinkauthnm = AuthCode.NOSTART.getMsg();
        }else if(islinkauth.equals(AuthCode.AUTHING.getCode())) {
            this.islinkauthnm = AuthCode.AUTHING.getMsg();
        }
        this.islinkauth = islinkauth;
    }

    public String getIsworkauth() {
        return isworkauth;
    }

    public void setIsworkauth(String isworkauth) {
        if(isworkauth.equals(AuthCode.SUCCESS.getCode())) {
            this.isworkauthnm = AuthCode.SUCCESS.getMsg();
        }else if(isworkauth.equals(AuthCode.ERROR.getCode())) {
            this.isworkauthnm = AuthCode.ERROR.getMsg();
        }else if(isworkauth.equals(AuthCode.NOSTART.getCode())) {
            this.isworkauthnm = AuthCode.NOSTART.getMsg();
        }else if(isworkauth.equals(AuthCode.AUTHING.getCode())) {
            this.isworkauthnm = AuthCode.AUTHING.getMsg();
        }
        this.isworkauth = isworkauth;
    }

    public String getIsrealauthnm() {
        return isrealauthnm;
    }

    public void setIsrealauthnm(String isrealauthnm) {
        this.isrealauthnm = isrealauthnm;
    }

    public String getIsphoneauthnm() {
        return isphoneauthnm;
    }

    public void setIsphoneauthnm(String isphoneauthnm) {
        this.isphoneauthnm = isphoneauthnm;
    }

    public String getIseducauthnm() {
        return iseducauthnm;
    }

    public void setIseducauthnm(String iseducauthnm) {
        this.iseducauthnm = iseducauthnm;
    }

    public Long getKf_id() {
        return kf_id;
    }

    public void setKf_id(Long kf_id) {
        this.kf_id = kf_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getHaswork() {
        return haswork;
    }

    public void setHaswork(Boolean haswork) {
        this.haswork = haswork;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposenm() {
        return purposenm;
    }

    public void setPurposenm(String purposenm) {
        this.purposenm = purposenm;
    }

    public String getEducationtype() {
        return educationtype;
    }

    public void setEducationtype(String educationtype) {
        this.educationtype = educationtype;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSpecialitiename() {
        return specialitiename;
    }

    public void setSpecialitiename(String specialitiename) {
        this.specialitiename = specialitiename;
    }

    public String getTimeenrollment() {
        return timeenrollment;
    }

    public void setTimeenrollment(String timeenrollment) {
        this.timeenrollment = timeenrollment;
    }

    public String getDategraduation() {
        return dategraduation;
    }

    public void setDategraduation(String dategraduation) {
        this.dategraduation = dategraduation;
    }

    public String getGraduationstatus() {
        return graduationstatus;
    }

    public void setGraduationstatus(String graduationstatus) {
        this.graduationstatus = graduationstatus;
    }

    public Long getEduc_id() {
        return educ_id;
    }

    public void setEduc_id(Long educ_id) {
        this.educ_id = educ_id;
    }

    public Long getWork_id() {
        return work_id;
    }

    public void setWork_id(Long work_id) {
        this.work_id = work_id;
    }

    public String getIslinkauthnm() {
        return islinkauthnm;
    }

    public void setIslinkauthnm(String islinkauthnm) {
        this.islinkauthnm = islinkauthnm;
    }

    public String getIsworkauthnm() {
        return isworkauthnm;
    }

    public void setIsworkauthnm(String isworkauthnm) {
        this.isworkauthnm = isworkauthnm;
    }

    public String getKf_name() {
        return kf_name;
    }

    public void setKf_name(String kf_name) {
        this.kf_name = kf_name;
    }

    public String getKf_wechat() {
        return kf_wechat;
    }

    public void setKf_wechat(String kf_wechat) {
        this.kf_wechat = kf_wechat;
    }

    public String getLinkapplydate() {
        return linkapplydate;
    }

    public void setLinkapplydate(String linkapplydate) {
        this.linkapplydate = linkapplydate;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDorm() {
        return dorm;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLoginoutdate() {
        return loginoutdate;
    }

    public void setLoginoutdate(String loginoutdate) {
        this.loginoutdate = loginoutdate;
    }
}
