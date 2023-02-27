package com.kamron.demo2.controller;

import com.kamron.demo2.common.ResponseData;
import com.kamron.demo2.dto.BookDto;
import com.kamron.demo2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseData<?> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public ResponseData<?> addBook(@RequestBody final BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/authors")
    public ResponseData<?> getAllBooksGroupedByAuthor() {
        return bookService.findAllGroupedByAuthor();
    }

    @GetMapping("/authors/char")
    public ResponseData<?> getAuthorsWithChar(@RequestParam("char") final char character) {
        return bookService.getAuthorsWithChar(character);
    }
}
