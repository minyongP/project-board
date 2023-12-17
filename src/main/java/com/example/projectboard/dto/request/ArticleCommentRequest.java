package com.example.projectboard.dto.request;

import com.example.projectboard.dto.ArticleCommentDto;
import com.example.projectboard.dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleCommentRequest {
    private Long articleId;
    private String content;

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
