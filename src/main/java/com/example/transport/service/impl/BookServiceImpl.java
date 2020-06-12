package com.example.transport.service.impl;

import com.example.transport.bean.Book;
import com.example.transport.bean.BookRent;
import com.example.transport.bean.User;
import com.example.transport.dao.BookMapper;
import com.example.transport.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<Book> getAll() {
        return bookMapper.getAll();
    }

    @Override
    public Book findBook(int id) {
        return bookMapper.findBook(id);
    }

    @Override
    public String rent(String name) {

        System.out.println("impl-rent"+name);
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("impl-rent"+user.getUserId());
        //查看是否已租借该书籍
        if(bookMapper.findRentBook(name,user.getUserId()).size()>0){
            return "您已租借该书籍";
        }
        //图书数量-1
        bookMapper.rent(name);
        //插入租借记录
        bookMapper.addRent(name,user.getUserId());
        return "租借成功";
    }

    //查找该userId租借的所有书籍
    @Override
    public List<Book> findRent(String userId) {
        return bookMapper.findRent(userId);
    }

    @Override
    @Transactional
    public void back(String name, String userId) {
        bookMapper.back(name);
        bookMapper.backRent(name,userId);
    }

    @Override
    public List<Book> getByName(String name) {
        name = "%"+name+"%";
        List<Book> list = bookMapper.getByName(name);
        return list;
    }

    @Override
    public List<Book> getByType(String type) {
        List<Book> list = bookMapper.getByType(type);
        return list;
    }

    @Override
    public void update(int id, String name, String content, String type, String num, String imgUrl) {
        bookMapper.update(id,name,content,type,num,imgUrl);
    }

    @Override
    public void add(String name, String content, String type, String num, String imgUrl) {
        bookMapper.add(name,content,type,num,imgUrl);

    }

    @Override
    public void bookDelete(int id) {
        bookMapper.delete(id);
    }
}
