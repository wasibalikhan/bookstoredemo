package com.wak.bookstore.bookstore.service;

import com.wak.bookstore.bookstore.entity.Book;

import java.util.List;

public interface BookService {

    public Book findBookByName(String bookName);
    public List<Book> getCompleteBookList();
    public Book updateBook(Book book, Integer bookId);
}
