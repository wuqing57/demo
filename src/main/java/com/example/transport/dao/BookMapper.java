package com.example.transport.dao;

import com.example.transport.bean.Book;
import com.example.transport.bean.BookRent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BookMapper {
    List<Book>  getAll();

    Book findBook(int id);

    void  rent(String name);

    void  addRent(String name, String userId);

    List<Book>   findRent(String userId);

    List<BookRent>   findRentBook(String name, String userId);

    void back(String name);

    void backRent(String name, String userId);

    List<Book>  getByName(String name);

    List<Book>  getByType(String type);

    void  update(int id, String name, String content, String type, String num, String imgUrl);

    void  add(String name, String content, String type, String num, String imgUrl);

    void  delete(int id);
}