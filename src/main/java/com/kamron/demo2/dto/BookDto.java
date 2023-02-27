package com.kamron.demo2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private List<BookDto> books;

    public BookDto(final String author, final List<BookDto> bookDtos) {
        this.author = author;
        this.books = bookDtos;
    }

    public BookDto(final Long id, @NonNull final String title, @NonNull final String author, final String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }
}
