package com.kamron.demo2.service;

import com.kamron.demo2.common.ResponseData;
import com.kamron.demo2.dto.BookDto;

public interface BookService {
    ResponseData<?> findAll();
    ResponseData<?> save(BookDto bookDto);
    ResponseData<?> findAllGroupedByAuthor();
    ResponseData<?> getAuthorsWithChar(char character);
}
