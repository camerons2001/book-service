package com.kamron.demo2.service;

import com.kamron.demo2.common.ResponseData;
import com.kamron.demo2.dto.AuthorCountDto;
import com.kamron.demo2.dto.BookDto;
import com.kamron.demo2.model.Book;
import com.kamron.demo2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public ResponseData<?> findAll() {
        log.info("Fetching all books from the database, sorted by title in reverse alphabetical order...");
        final List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "title"));
        final List<BookDto> bookDtos = books.stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()))
                .collect(Collectors.toList());
        log.info("Returning {} books from the database.", bookDtos.size());
        return ResponseData.response(bookDtos).getBody();
    }

    @Override
    public ResponseData<?> save(final BookDto bookDto) {
        log.info("Saving book {} to the database...", bookDto.getTitle());
        final Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .description(bookDto.getDescription())
                .build();
        bookRepository.save(book);
        log.info("Book {} saved successfully to the database.", bookDto.getTitle());
        return ResponseData.response(new ResponseData<>(bookDto), HttpStatus.CREATED).getBody();
    }

    @Override
    public ResponseData<?> findAllGroupedByAuthor() {
        log.info("Fetching all books from the database and grouping them by author...");
        final List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "title"));
        final Map<String, List<Book>> booksByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
        final List<BookDto> result = new ArrayList<>();
        booksByAuthor.forEach((author, booksList) -> {
            final List<BookDto> bookDtos = booksList.stream()
                    .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()))
                    .collect(Collectors.toList());
            result.add(new BookDto(author, bookDtos));
        });
        log.info("Returning {} authors and their books from the database.", result.size());
        return ResponseData.response(result).getBody();
    }

    @Override
    public ResponseData<?> getAuthorsWithChar(final char character) {
        log.info("Fetching authors with the character '{}' in any of their book titles from the database...", character);
        final List<Book> books = bookRepository.findAll();
        final String charStr = Character.toString(character).toLowerCase();
        final List<AuthorCountDto> authorCounts = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(charStr))
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.summingInt(book -> {
                    final String title = book.getTitle().toLowerCase();
                    return (int) title.chars().filter(ch -> ch == character).count();
                })))
                .entrySet().stream()
                .map(entry -> new AuthorCountDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(AuthorCountDto::getCharCount).reversed())
                .limit(10)
                .collect(Collectors.toList());

        log.info("Returning {} authors with the character '{}' in any of their book titles from the database.", authorCounts.size(), character);
        return ResponseData.response(authorCounts).getBody();
    }
}
