package com.example.projectboard.dto.response;

import com.example.projectboard.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    public static ArticleResponse of(Long id, String title, String content, String hashtag, String email, String nickname, LocalDateTime createdAt) {
        return new ArticleResponse(id, title, content, hashtag, email, nickname, createdAt);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getCreatedAt()
        );
    }

}
