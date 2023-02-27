package com.kamron.demo2.repository;

import com.kamron.demo2.model.Book;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookRepository {
    List<Book> findAll(Sort title);
    List<Book> findAll();
    void save(Book book);
}
