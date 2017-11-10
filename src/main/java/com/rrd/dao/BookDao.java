package com.rrd.dao;

import com.rrd.model.Bill;
import com.rrd.model.Book;
import com.rrd.model.Product;
import com.rrd.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xinghb on 2017/10/23.
 */
public interface BookDao {
    List<Book> getBookList(@Param("book") Book book, @Param("user") User user);

    void delBook(@Param("ids") List<String> id);

    void addBook(Book book);

    Product getProductInfo(@Param("product_id") Long product_id);

    void addBill(@Param("bills") List<Bill> bills);

    void delBill(@Param("ids") List<String> ids);

    List<Bill> getBIllList(@Param("book_id") String book_id);

    void addBookOperLog(@Param("book_id") Long book_id, @Param("book_no") String book_no,
                        @Param("custom_id") Long custom_id, @Param("cusname") String cusname,
                        @Param("state") int state, @Param("username") String username,
                        @Param("desc") String desc);

    Book getBookById(@Param("id") String id);

    void updateBookRepay(Book book);

    Bill getBillById(@Param("id") String id);

    void updBill(Bill bill);

    void updateBookFeeInfo(Book book);

    void updBookWithBill(@Param("book_id") Long book_id);

    List<Map<String,Object>> getAuditBook(Book book);

    void refusetAudit(Book book);

    void passAudit(Book book);

    void updBillToComp(@Param("book_id") Long book_id);

    List<Map<String,Object>> getOperDesc(@Param("param") Map<String, String> param);

    List<Book> getAllBook();

    void updateExprodBill();

    void updateCurrBill();

    void updateNoStartToCurrBill(@Param("timeday") int timeday);

    void updBookState();

    List<Book> getBookWithYq();

    void intsertMsg(String custom_id, String content);

    Map<String, Object> getStateBill(@Param("param") Map<String, String> param);

    Map<String,Object> getRepayBill(@Param("param") Map<String, String> param);

    List<Map<String,Object>> getBillBasefeeList(@Param("param") Map<String, String> param);

    List<Map<String,Object>> getBillRepayFeeList(@Param("param") Map<String, String> param);

    List<Product> getAllProduct();

    List<Map<String,Object>> billstatedet(@Param("param") Map<String, String> param);

    Map<String,Object> getCustomInfo(@Param("book_id") String book_id);
}
