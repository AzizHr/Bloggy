package com.example.bloggy.service;

import com.example.bloggy.dto.article.ArticleDTO;
import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ArticleService {

    ArticleResponse save(ArticleDTO articleDTO) throws NotFoundException;
    ArticleResponse update(ArticleDTO articleDTO) throws NotFoundException;
    void delete(String id) throws NotFoundException;
    ArticleResponse findById(String id) throws NotFoundException;
    Page<ArticleResponse> findAll(Pageable pageable) throws NotFoundException;
    Page<ArticleResponse> findByAuthor(String author, Pageable pageable) throws NotFoundException;
    Page<ArticleResponse> searchByTitle(String title, Pageable pageable) throws NotFoundException;
    Page<ArticleResponse> searchByContent(String content, Pageable pageable) throws NotFoundException;
    Page<ArticleResponse> findByTag(String tag, Pageable pageable) throws NotFoundException;

}
