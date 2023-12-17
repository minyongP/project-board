package com.example.projectboard.controller;

import com.example.projectboard.dto.UserAccountDto;
import com.example.projectboard.dto.request.ArticleCommentRequest;
import com.example.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleCommnet(ArticleCommentRequest articleCommentRequest) {
        //todo: 인증 정보 필요
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of("uno", "pw", "uno@mail.com", null, null)));

        return "redirect:/articles/" + articleCommentRequest.getArticleId();
    }

    @PostMapping("{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
