package com.kamron.demo2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorCountDto {
    private String author;
    private int charCount;
}
