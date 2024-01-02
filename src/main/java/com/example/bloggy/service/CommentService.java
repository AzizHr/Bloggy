package com.example.bloggy.service;

import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Comment;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CommentService {

    ArticleResponse save(Comment comment) throws NotFoundException;
    ArticleResponse update(Comment comment) throws NotFoundException;
    void delete(String id) throws NotFoundException;
    CommentResponse findById(String id) throws NotFoundException;
    List<CommentResponse> findAll() throws NotFoundException;
    List<CommentResponse> findByArticle(String article) throws NotFoundException;

}
