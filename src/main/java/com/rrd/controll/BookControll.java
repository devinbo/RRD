package com.rrd.controll;

import com.github.pagehelper.PageInfo;
import com.rrd.model.Bill;
import com.rrd.model.Book;
import com.rrd.pjo.Result;
import com.rrd.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by xinghb on 2017/10/23.
 */
@RestController
@RequestMapping("book")
public class BookControll {

    @Autowired
    private BookService bookService;

    @PostMapping("getBookList")
    public PageInfo getBookList(PageInfo pageInfo, Book book) {
        return bookService.getBookList(pageInfo, book);
    }

    @PostMapping("delBook")
    public Result delBook(String ids) {
        return bookService.delBook(ids);
    }

    @PostMapping("andBook")
    public Result andBook(Book book) throws Exception {
        return bookService.addBook(book);
    }

    @PostMapping("getBillList")
    public PageInfo getBillList(PageInfo pageInfo, String book_id) {
        return bookService.getBillList(pageInfo, book_id);
    }

    @PostMapping("getBillById")
    public Result getBillById(String id) {
        return bookService.getBIllById(id);
    }

    @PostMapping("updBill")
    public Result updBill(Bill bill) {
        return bookService.updBill(bill);
    }

    @PostMapping("getAuditBook")
    public PageInfo getAuditBook(PageInfo pageInfo, Book book) {
        return bookService.getAuditBook(pageInfo, book);
    }

    @PostMapping("refuseAudit")
    public Result refuseAudit(Book book) {
        return bookService.refuseAudit(book);
    }

    @PostMapping("passAudit")
    public Result passAudit(Book book) {
        return bookService.passAudit(book);
    }

    @PostMapping("completeBook")
    public Result completeBook(Book book) {
        return bookService.completeBook(book);
    }

    @PostMapping("getOperDesc")
    public PageInfo getOperDesc(PageInfo pageInfo, @RequestParam Map<String, String> param) {
        return bookService.getOperDesc(pageInfo, param);
    }

    @PostMapping("billStat")
    public Result billStat(@RequestParam Map<String, String> param) {
        return bookService.billStat(param);
    }

    @PostMapping("billstatedet")
    public PageInfo billstatedet(PageInfo pageInfo, @RequestParam Map<String, String>param) {
        return bookService.billstatedet(pageInfo, param);
    }
}
