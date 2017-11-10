package com.rrd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rrd.dao.BookDao;
import com.rrd.model.Bill;
import com.rrd.model.Book;
import com.rrd.model.Product;
import com.rrd.model.User;
import com.rrd.pjo.Result;
import com.rrd.schedule.ComThreadPool;
import com.rrd.schedule.SendDxTask;
import com.rrd.service.BookService;
import com.rrd.utils.PublicUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * Created by xinghb on 2017/10/23.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private HttpServletRequest request;

    @Override
    public PageInfo getBookList(PageInfo pageInfo, Book book) {
        PageHelper.startPage(pageInfo);
        List<Book> list = bookDao.getBookList(book, getUser());
        return new PageInfo<>(list);
    }

    @Override
    public Result delBook(String ids) {
        List<String> idList = PublicUtil.toListByIds(ids);
        //删除订单表
        bookDao.delBook(idList);
        //删除费用表
        bookDao.delBill(idList);
        //添加操作记录
        User user = getUser();
        for (String id : idList) {
            Book book = bookDao.getBookById(id);
            //加入操作记录
            bookDao.addBookOperLog(Long.valueOf(id), book.getBook_no(), book.getCustom_id(), book.getCusname(), 4, user.getUsername(), "删除订单");
        }
        return new Result();
    }

    /**
     * 生成订单
     *
     * @param book
     * @return
     */
    @Override
    public Result addBook(Book book) throws Exception {
        User user = getUser();
        //生成主订单信息
        book.setBook_no(PublicUtil.getNoID("LXD"));
        book.setCrtuser(user.getUsername());
        bookDao.addBook(book);
        //获取产品信息
        Product product = bookDao.getProductInfo(book.getProduct_id());
        System.out.println(product.getDeadunit());
        List<Bill> bills = createBill(product, book);
        //生成费用信息
        bookDao.addBill(bills);
        //添加操作记录
        bookDao.addBookOperLog(book.getBook_id(), book.getBook_no(), book.getCustom_id(), book.getCusname(), 1, user.getUsername(), "创建订单");
        return new Result();
    }

    @Override
    public PageInfo getBillList(PageInfo pageInfo, String book_id) {
        PageHelper.startPage(pageInfo);
        List<Bill> list = bookDao.getBIllList(book_id);
        return new PageInfo<>(list);
    }

    @Override
    public Result getBIllById(String id) {
        Bill bill = bookDao.getBillById(id);
        return new Result(bill);
    }

    @Override
    public Result updBill(Bill bill) {
        //获取原有费用信息
        Bill billDb = bookDao.getBillById(bill.getId().toString());
        //修改账单信息
        bill.setUpduser(getUser().getUsername());
        bookDao.updBill(bill);

        StringBuilder sbd = new StringBuilder();
        sbd.append("对第" + billDb.getPeriod() + "期，账单进行修改操作；具体操作为：");
        if (!Objects.equals(bill.getStatenm(), billDb.getStatenm())) {
            sbd.append("原有订单状态：" + (billDb.getStatenm() == null ? "0" : billDb.getStatenm()) + ",修改为：" + bill.getStatenm() + ";");
        }
        if (!decimalEqual(bill.getExpiryfee(), billDb.getExpiryfee())) {
            sbd.append("原有逾期费：" + (billDb.getExpiryfee() == null ? "0" : billDb.getExpiryfee()) + ",修改为：" + bill.getExpiryfee() + ";");
        }
        if (!decimalEqual(bill.getServicefee(), billDb.getServicefee())) {
            sbd.append("原有服务费：" + (billDb.getServicefee() == null ? "0" : billDb.getServicefee()) + ",修改为：" + bill.getServicefee() + ";");
        }
        if (!Objects.equals(bill.getTargetdate(), billDb.getTargetdate())) {
            sbd.append("原有预计还款日：" + (billDb.getTargetdate() == null ? "" : billDb.getTargetdate()) + ",修改为：" + bill.getTargetdate() + ";");
        }
        if (!Objects.equals(bill.getRealdate(), billDb.getRealdate())) {
            sbd.append("原有实际还款日：" + (billDb.getRealdate() == null ? "" : billDb.getRealdate()) + ",修改为：" + bill.getRealdate() + ";");
        }

        //根据账单 更新订单费用
        bookDao.updBookWithBill(billDb.getBook_id());
        return new Result();
    }

    @Override
    public PageInfo getAuditBook(PageInfo pageInfo, Book book) {
        PageHelper.startPage(pageInfo);
        List<Map<String, Object>> list = bookDao.getAuditBook(book);
        return new PageInfo<>(list);
    }

    @Override
    public Result refuseAudit(Book book) {
        bookDao.refusetAudit(book);
        Book bookDb = bookDao.getBookById(book.getBook_id().toString());
        bookDao.addBookOperLog(book.getBook_id(), bookDb.getBook_no(), bookDb.getCustom_id(),
                bookDb.getCusname(), 3, getUser().getUsername(), "订单审核通过");
        return new Result();
    }

    @Override
    public Result passAudit(Book book) {
        Book bookDb = bookDao.getBookById(book.getBook_id().toString());
        bookDao.addBookOperLog(book.getBook_id(), bookDb.getBook_no(), bookDb.getCustom_id(),
                bookDb.getCusname(), 3, getUser().getUsername(), "订单审核通过");
        //开始生成费用信息
        Product product = bookDao.getProductInfo(bookDb.getProduct_id());
        bookDb.setStart_date(PublicUtil.getDate("yyyy-MM-dd"));
        List<Bill> bills = createBill(product, bookDb);
        bookDao.addBill(bills);
        //生成订单费用信息，
        bookDao.passAudit(book);

        //加入短信通知业务
        Map<String, Object> map = bookDao.getCustomInfo(book.getBook_id().toString());
        SendDxTask sendDxTask = new SendDxTask(map.get("name").toString(), map.get("book_no").toString(), map.get("phone").toString());
        ComThreadPool.addTask(sendDxTask);
        return new Result();
    }

    @Override
    public Result completeBook(Book book) {
        //设置该订单的所有账单为已完结状态
        bookDao.updBillToComp(book.getBook_id());
        bookDao.updBookWithBill(book.getBook_id());
        //进入操作纪律
        Book bookDb = bookDao.getBookById(book.getBook_id().toString());
        bookDao.addBookOperLog(book.getBook_id(), bookDb.getBook_no(),
                bookDb.getCustom_id(), bookDb.getCusname(), 5,
                getUser().getUsername(), "完结该订单，结清该订单所有费用");
        return new Result();
    }

    @Override
    public PageInfo getOperDesc(PageInfo pageInfo, Map<String, String> param) {
        PageHelper.startPage(pageInfo);
        List<Map<String, Object>> list = bookDao.getOperDesc(param);
        return new PageInfo<>(list);
    }

    @Override
    public Result billStat(Map<String, String> param) {
        //生成费用
        if (!StringUtils.isEmpty(param.get("start_date"))) {
            //查询固定时间段
            //固定时间段的基本费用
            Map<String, Object> basefee = bookDao.getStateBill(param);
            //固定时间段还款费用
            Map<String, Object> repayfee = bookDao.getRepayBill(param);
            Map<String, Object> res = new HashMap<>();
            if(basefee != null) {
                res.putAll(basefee);
            }
            if(repayfee != null) {
                res.putAll(repayfee);
            }
            return new Result(res);
        } else {
            //查询多期费用信息
            List<Map<String, Object>> basefeelist = bookDao.getBillBasefeeList(param);
            List<Map<String, Object>> repayfeelist = bookDao.getBillRepayFeeList(param);
            Map<String, Object> result = new HashMap<>();
            System.out.println(basefeelist);
            result.put("basefee", PublicUtil.toSingList(basefeelist, "basefee"));
            result.put("repayfee", PublicUtil.toSingList(repayfeelist, "repayfee"));
            return new Result(result);
        }
    }

    @Override
    public PageInfo billstatedet(PageInfo pageInfo, Map<String, String>param) {
        PageHelper.startPage(pageInfo);
        //查询具体时间的 账单
        List<Map<String, Object>> resultList = bookDao.billstatedet(param);
        return new PageInfo<>(resultList);
    }

//    @Override
//    public Result updBill(Bill bill) {
//        //获取原有费用信息
//        Bill billDb = bookDao.getBillById(bill.getId().toString());
//        //修改账单信息
//        bill.setUpduser(getUser().getUsername());
//        bookDao.updBill(bill);
//        //剩余应还
//        BigDecimal surpayWithBook = new BigDecimal(0);
//        //当前应还
//        BigDecimal repayWithBook = new BigDecimal(0);
//        //总逾期费
//        BigDecimal expfeeWithBook = new BigDecimal(0);
//        //服务费
//        BigDecimal servfeeWithBook = new BigDecimal(0);
//
//        //对比差异列，生成描述信息
//        StringBuilder sbd = new StringBuilder();
//        sbd.append("对第" + billDb.getPeriod() + "期，账单进行修改操作；具体操作为：");
//        if (!Objects.equals(bill.getStatenm(), billDb.getStatenm())) {
//            sbd.append("原有订单状态：" + (billDb.getStatenm() == null ? "" : billDb.getStatenm()) + ",修改为：" + bill.getStatenm() + ";");
//            /*
//             * 本次逻辑判断 只判断应收，剩余费用
//             */
//            if ("2".equals(billDb.getState())) { //订单从其他状态变成已结清状态， 去掉所有的费用，
//                repayWithBook = repayWithBook.add(bill.getRepayfee());
//                //剩余应还 + 基本费用
//                surpayWithBook = surpayWithBook.add(bill.getBasefee());
//            }else if ("2".equals(bill.getState())) { //订单从其他状态 变为结清，
//                //当前费用状态 变成了已结清， 那么原有订单应还金额-本期基本费用
//                repayWithBook = repayWithBook.subtract(bill.getRepayfee());
//                surpayWithBook = surpayWithBook.subtract(bill.getBasefee());
//            } else {
//                //计算2次应还费用差
//                repayWithBook = bill.getRepayfee().subtract(billDb.getRepayfee());
//            }
//            /*
//                本次逻辑判断 处理逾期，服务费.
//                由逾期 ---》 其他， 需要减去本期逾期费
//                由其他 ---》 逾期， 需要 + 本期逾期费
//             */
//            if("3".equals(bill.getState())) {
//                expfeeWithBook = expfeeWithBook.add(bill.getExpiryfee() == null ? new BigDecimal(0) : bill.getExpiryfee());
//                servfeeWithBook = servfeeWithBook.add(bill.getServicefee() == null ? new BigDecimal(0) : bill.getServicefee());
//            }else if("3".equals(billDb.getState())) {
//                expfeeWithBook = expfeeWithBook.subtract(bill.getExpiryfee() == null ? new BigDecimal(0) : bill.getExpiryfee());
//                servfeeWithBook = servfeeWithBook.subtract(bill.getServicefee() == null ? new BigDecimal(0) : bill.getServicefee());
//            }
//        } else {
//            //计算2次应还费用差
//            repayWithBook = bill.getRepayfee().subtract(billDb.getRepayfee());
//            if ("3".equals(bill.getState())) { //如果当前状态为逾期状态， 那么逾期费用 才会添加到book表
//                //逾期费发生变化，计算差价
//                expfeeWithBook = bill.getExpiryfee().subtract((billDb.getExpiryfee() == null ? new BigDecimal(0) : billDb.getExpiryfee()));
//                servfeeWithBook = bill.getServicefee().subtract(billDb.getServicefee() == null ? new BigDecimal(0) : billDb.getServicefee());
//            }
//        }
//        if (!decimalEqual(bill.getExpiryfee(), billDb.getExpiryfee())) {
//            sbd.append("原有逾期费：" + (billDb.getExpiryfee() == null ? "" : billDb.getExpiryfee()) + ",修改为：" + bill.getExpiryfee() + ";");
//        }
//        if (!decimalEqual(bill.getServicefee(), billDb.getServicefee())) {
//            sbd.append("原有服务费：" + (billDb.getServicefee() == null ? "" : billDb.getServicefee()) + ",修改为：" + bill.getServicefee() + ";");
//        }
//        if (!Objects.equals(bill.getTargetdate(), billDb.getTargetdate())) {
//            sbd.append("原有预计还款日：" + (billDb.getTargetdate() == null ? "" : billDb.getTargetdate()) + ",修改为：" + bill.getTargetdate() + ";");
//        }
//        if (!Objects.equals(bill.getRealdate(), billDb.getRealdate())) {
//            sbd.append("原有实际还款日：" + (billDb.getRealdate() == null ? "" : billDb.getRealdate()) + ",修改为：" + bill.getRealdate() + ";");
//        }
//        Book book = new Book();
//        book.setBook_id(billDb.getBook_id());
//        book.setCurrrepay(repayWithBook);
//        book.setRemainfee(surpayWithBook);
//        book.setExpiryfee(expfeeWithBook);
//        book.setServerfee(servfeeWithBook);
//        bookDao.updateBookFeeInfo(book);
//        //插入描述信息
//        bookDao.addBookOperLog(billDb.getBook_id(), billDb.getBook_no(),
//                billDb.getCustom_id(), billDb.getCusname(),
//                2, getUser().getUsername(), sbd.toString());
//        return new Result();
//    }

    private boolean decimalEqual(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2) == 0;
    }

    /**
     * 创建费用信息
     *
     * @param product
     * @param book
     * @return
     */
    private List<Bill> createBill(Product product, Book book) {
        List<Bill> billList = new ArrayList<>();
        //期限
        Integer deadLine = product.getDeadline();
        //期限单位  1: 天， 2：周， 3：月， 4：年
        String deadunit = product.getDeadunit();
        //开始借贷日
        String start_date = book.getStart_date();
        //当前日期
        Long nowTime = new Date().getTime();
        BigDecimal currRepay = new BigDecimal(0); //当前应还
        BigDecimal repayedfee = new BigDecimal(0); //已经还款金额
        String repaydate = null;
        String bookstate = "4";
        for (int i = 0; i < deadLine; i++) {
            Date s_date = PublicUtil.strDateToDate(start_date, "yyyy-MM-dd");
            Bill bill = new Bill();
            //设置产品名称
            bill.setProduct(product.getName());
            //设置订单号
            bill.setBook_id(book.getBook_id());
            //设置期限，（第几期）
            bill.setPeriod(String.valueOf(i + 1));
            Date repayDate = null;
            //生成待还款日(根据开始借贷日生成借贷信息)
            switch (deadunit) {
                case "1":
                    repayDate = DateUtils.addDays(s_date, 1);
                    start_date = PublicUtil.toDateStr(repayDate, "yyyy-MM-dd");
                    bill.setTargetdate(start_date);
                    break;
                case "2":
                    repayDate = DateUtils.addWeeks(s_date, 1);
                    start_date = PublicUtil.toDateStr(DateUtils.addWeeks(s_date, 1), "yyyy-MM-dd");
                    bill.setTargetdate(start_date);
                    break;
                case "3":
                    repayDate = DateUtils.addMonths(s_date, 1);
                    start_date = PublicUtil.toDateStr(DateUtils.addMonths(s_date, 1), "yyyy-MM-dd");
                    bill.setTargetdate(start_date);
                    break;
                case "4":
                    repayDate = DateUtils.addYears(s_date, 1);
                    start_date = PublicUtil.toDateStr(DateUtils.addYears(s_date, 1), "yyyy-MM-dd");
                    bill.setTargetdate(start_date);
                    break;
                default:  //默认每周
                    repayDate = DateUtils.addWeeks(s_date, 1);
                    start_date = PublicUtil.toDateStr(DateUtils.addWeeks(s_date, 1), "yyyy-MM-dd");
                    bill.setTargetdate(start_date);
                    break;
            }
            //如果目标还款日<当前日期， 那么认为已经还款成功，
            if (repayDate.getTime() < nowTime) {
                //说明已还过
                bill.setState("2");
                bill.setRealdate(start_date);
                //获取已还款金额
                repayedfee = repayedfee.add(product.getDeadprice());
            } else if (repayDate.getTime() > nowTime && s_date.getTime() < nowTime) {
                //说明为当期为待还
                bill.setState("4");
                //那么修改费用为当前应还金额。
                currRepay = product.getDeadprice();
                repaydate = start_date;
                bookstate = "3";
            } else {
                //说明尚未开始
                bill.setState("1");
            }
            //基本费用
            bill.setBasefee(product.getDeadprice());
            //设置应还=基本费用
            bill.setRepayfee(bill.getBasefee());
            bill.setCrtuser(getUser().getUsername());
            billList.add(bill);
        }

        //剩余费用，
        System.out.println(repayedfee);
        System.out.println(product.getPrice());
        BigDecimal remainfee = product.getPrice().subtract(repayedfee);
        book.setCurrrepay(currRepay);
        book.setRemainfee(remainfee);
        book.setBook_state(bookstate);
        book.setRepaydate(repaydate);
        //更新订单信息
        bookDao.updateBookRepay(book);
        return billList;
    }

//    private Date nextDate(Date s_date, String deadunit) {
//        Date repayDate = null;
//        switch (deadunit) {
//            case "1":
//                repayDate = DateUtils.addDays(s_date, 1);
//                break;
//            case "2":
//                repayDate = DateUtils.addWeeks(s_date, 1);
//                break;
//            case "3":
//                repayDate = DateUtils.addMonths(s_date, 1);
//                break;
//            case "4":
//                repayDate = DateUtils.addYears(s_date, 1);
//                break;
//            default:  //默认每周
//                repayDate = DateUtils.addWeeks(s_date, 1);
//                break;
//        }
//        return repayDate;
//    }

    private User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
