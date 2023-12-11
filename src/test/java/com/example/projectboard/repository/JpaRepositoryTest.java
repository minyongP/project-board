package com.example.projectboard.repository;

import com.example.projectboard.config.JpaConfig;
import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                      @Autowired ArticleCommentRepository articleCommentRepository,
                      @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert test")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // given
        long previousCnt = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("newMY", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");
        // when
        articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCnt + 1);
    }

    @DisplayName("update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);

        // when
        Article saveArticle = articleRepository.saveAndFlush(article); // 롤백되려 하기에 로그에 update가 보이지 않으므로 flush 추가

        //then
        assertThat(saveArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }

    @DisplayName("delete test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousCnt = articleRepository.count();
        long previousCommentCnt = articleCommentRepository.count();
        long deletedCommentSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCnt - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousCommentCnt - deletedCommentSize);
    }
}
