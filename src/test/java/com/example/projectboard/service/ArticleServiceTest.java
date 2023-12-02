package com.example.projectboard.service;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.type.SearchType;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.ArticleUpdateDto;
import com.example.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비지니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면 게시글, 리스트 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        //given

        //when
        Page<ArticleDto> articles = articleService.searchArticles(SearchType.TITLE, "search keyword");

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글 반환")
    @Test
    void givenArticledId_whenSearchingArticle_thenReturnsArticle() {
        //given

        //when
        ArticleDto articles = articleService.searchArticle(1L);

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글 생성")
    @Test
    void givenArticleInfo_thenSavingArticle_thenSavesArticle() {
        //given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        articleService.saveArticle(ArticleDto.of(LocalDateTime.now(), "my", "title", "content", "hashtag"));

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 id와 수정 정보 입력하면, 게시글 수정")
    @Test
    void givenArticleIdAndModifiedInfo_thenUpdateArticle_thenUpdatesArticle() {
        //given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        articleService.updateArticle(1L, ArticleUpdateDto.of("title", "content", "hashtag"));

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 id를 입력하면, 게시글 삭제")
    @Test
    void givenArticleId_thenDeletingArticle_thenDeletesArticle() {
        //given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        articleService.deleteArticle(1L);

        //then
        then(articleRepository).should().delete(any(Article.class));
    }
}
