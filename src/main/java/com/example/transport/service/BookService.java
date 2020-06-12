package com.example.transport.service;

import com.example.transport.bean.Book;

import java.util.List;

public interface BookService {
    List<Book>  getAll();

    Book   findBook(int id);

    String  rent(String name);

    List<Book>  findRent(String userId);

    void  back(String name, String userId);

    List<Book>  getByName(String name);

    List<Book>  getByType(String type);

    void  update(int id, String name, String content, String type, String num, String imgUrl);

    void  add(String name, String content, String type, String num, String imgUrl);

    void  bookDelete(int id);
}
