package com.wak.bookstore.bookstore.repository;

import com.wak.bookstore.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByName(String name);

}
