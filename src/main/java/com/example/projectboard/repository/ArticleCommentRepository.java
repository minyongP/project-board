package com.example.projectboard.repository;

import com.example.projectboard.domain.ArticleComent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComent, Long> {
}
