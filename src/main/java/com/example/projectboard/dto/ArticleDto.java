package com.example.projectboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ArticleDto {
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final String title;
    private final String content;
    private final String hashtag;

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
}