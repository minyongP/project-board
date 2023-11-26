package com.example.projectboard.domain;

import java.time.LocalDateTime;

public class ArticleComent {
    private Long id;
    private Article article;
    private String hashtag;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
