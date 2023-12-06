package com.example.projectboard.response;

import com.example.projectboard.dto.ArticleCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleCommentResponse {
    private Long id;
    private String content;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    public static ArticleCommentResponse of(Long id, String content, String email, String nickname, LocalDateTime createdAt) {
        return new ArticleCommentResponse(id, content, email, nickname, createdAt);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleCommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getCreatedAt()
        );
    }

}
