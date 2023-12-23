package com.example.projectboard.dto.response;

import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.HashtagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private Set<String> hashtags;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    public static ArticleResponse of(Long id, String title, String content, Set<String> hashtags, String email, String nickname, LocalDateTime createdAt) {
        return new ArticleResponse(id, title, content, hashtags, email, nickname, createdAt);
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
                dto.getHashtagDtos().stream().map(HashtagDto::getHashtagName).collect(Collectors.toUnmodifiableSet()),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getCreatedAt()
        );
    }

}
