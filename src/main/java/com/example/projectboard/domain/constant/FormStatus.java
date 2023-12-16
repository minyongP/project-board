package com.example.projectboard.domain.constant;

import lombok.Getter;

public enum FormStatus {
    CREATE("저장", false),
    UPDATE("수정", true);

    @Getter private final String description;
    @Getter private final Boolean update;

    FormStatus(String description, Boolean update) {
        this.description = description;
        this.update = update;
    }

    public enum SearchType {
        TITLE("제목"),
        CONTENT("본문"),
        ID("유저 ID"),
        NICKNAME("닉네임"),
        HASHTAG("해시태그");

        @Getter private final String description;

        SearchType(String description) {
            this.description = description;
        }
    }
}
