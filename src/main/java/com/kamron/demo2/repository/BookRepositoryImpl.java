package com.kamron.demo2.repository;

import com.kamron.demo2.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll(final Sort title) {
        final String sql = "SELECT * FROM BOOK ORDER BY TITLE DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Book(rs.getLong("ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("DESCRIPTION"))
        );
    }

    @Override
    public List<Book> findAll() {
        final String sql = "SELECT * FROM BOOK";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Book(rs.getLong("ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("DESCRIPTION"))
        );
    }

    @Override
    public void save(final Book book) {
        final String sql = "INSERT INTO BOOK (TITLE, AUTHOR, DESCRIPTION) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getDescription());
    }
}
