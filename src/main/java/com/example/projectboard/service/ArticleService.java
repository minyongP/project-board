package com.example.projectboard.service;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.UserAccount;
import com.example.projectboard.domain.constant.SearchType;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.ArticleWithCommentsDto;
import com.example.projectboard.repository.ArticleRepository;
import com.example.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        switch (searchType) {
            case TITLE:
                return articleRepository.findByTitleContaining(searchKeyword, pageable)
                        .map(ArticleDto::from);
            case CONTENT:
                return articleRepository.findByContentContaining(searchKeyword, pageable)
                        .map(ArticleDto::from);
            case ID:
                return articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable)
                        .map(ArticleDto::from);
            case NICKNAME:
                return articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable)
                        .map(ArticleDto::from);
            case HASHTAG:
                return articleRepository.findByHashtagNames(
                        Arrays.stream(searchKeyword.split(" "))
                                .collect(Collectors.toList()),
                                pageable
                        )
                        .map(ArticleDto::from);
            default:
                return null;
        }
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다. - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId).map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다. - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.getTitle() != null) {
                    article.setTitle(dto.getTitle());
                }
                if (dto.getContent() != null) {
                    article.setContent(dto.getContent());
                }
            }
        }catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    public Long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtagNames(null, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }

}
