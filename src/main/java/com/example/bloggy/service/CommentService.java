package com.example.bloggy.service;

import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Comment;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CommentService {

    CommentResponse save(Comment comment) throws NotFoundException;
    CommentResponse update(Comment comment) throws NotFoundException;
    void delete(String id) throws NotFoundException;
    CommentResponse findById(String id) throws NotFoundException;
    List<CommentResponse> findAll() throws NotFoundException;
    List<CommentResponse> findByArticleId(String articleId) throws NotFoundException;

}
