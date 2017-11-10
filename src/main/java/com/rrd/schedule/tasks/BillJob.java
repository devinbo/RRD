package com.rrd.schedule.tasks;

import com.rrd.dao.BookDao;
import com.rrd.model.Book;
import com.rrd.model.Product;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xinghb on 2017/10/27.
 */
@Component
@Scope("prototype")
@Transactional(propagation = Propagation.REQUIRED)
public class BillJob implements Job{

    @Autowired
    private BookDao bookDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //更新逾期费用，+ 本期预期费用
        bookDao.updateExprodBill();
        //判断当前待还费用是否已经逾期， 如果逾期那么就生成逾期费用
        bookDao.updateCurrBill();
        //获取产品信息，
        List<Product> products = bookDao.getAllProduct();
        //产品单位
        for(Product product : products) {
            int timeday = 7;
            String deadunit = product.getDeadunit();
            switch (deadunit) {
                case "1" :
                    timeday = 1;
                    break;
                case "2" :
                    timeday = 7;
                    break;
                case "3" :
                    timeday = 30;
                    break;
                case "4" :
                    timeday = 365;
                    break;
                default:
                    timeday = 7;
                    break;
            }
            //如果判断尚未开始的费用是否需要转换为待还
            bookDao.updateNoStartToCurrBill(timeday);
        }
        //更新主订单状态
        bookDao.updBookState();
        //查询客户是否产生预期。如果产生逾期，那么就提示逾期费用
        List<Book> bookList = bookDao.getBookWithYq();
        for (Book book : bookList) {
            bookDao.intsertMsg(book.getCustom_id().toString(), "尊敬的用户，您的订单："+book.getBook_id()+"已经逾期；逾期费用："+book.getExpiryfee()+";请您尽快结清逾期的账单费用,以免影响您的信誉");
        }
    }

}
