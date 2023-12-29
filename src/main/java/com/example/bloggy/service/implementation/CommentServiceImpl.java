package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Article;
import com.example.bloggy.model.Comment;
import com.example.bloggy.model.User;
import com.example.bloggy.repository.ArticleRepository;
import com.example.bloggy.repository.CommentRepository;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ModelMapper modelMapper,
                              ArticleRepository articleRepository,
                              UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;

    }

    @Override
    public ArticleResponse save(Comment comment) throws NotFoundException {

        if(articleRepository.findById(comment.getArticle()).isPresent()) {
            Article article = articleRepository.findById(comment.getArticle()).get();
            User user = userRepository.findById(comment.getAuthor()).get();

            comment.setArticle(article.getId());
            comment.setAuthor(user.getId());
            comment.setCreatedAt(LocalDateTime.now());

            commentRepository.save(comment);
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            commentResponse.setAuthor(modelMapper.map(user, UserResponse.class));
            article.setComments(List.of(comment));
            return modelMapper.map(article, ArticleResponse.class);
        }
        throw new NotFoundException("No article was found with ID of "+ comment.getAuthor());

    }

    @Override
    public ArticleResponse update(Comment comment) throws NotFoundException {

        if(articleRepository.findById(comment.getArticle()).isPresent()) {
            Article article = articleRepository.findById(comment.getArticle()).get();
             User user = userRepository.findById(comment.getAuthor()).get();

             comment.setArticle(article.getId());
             comment.setAuthor(user.getId());

             commentRepository.save(comment);
             CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
             commentResponse.setAuthor(modelMapper.map(user, UserResponse.class));
             article.setComments(List.of(comment));
             return modelMapper.map(article, ArticleResponse.class);
        }
        throw new NotFoundException("No article was found with ID of "+ comment.getAuthor());

    }

    @Override
    public Void delete(String id) throws NotFoundException {
        if(commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
        }
        throw new NotFoundException("No comment was found with ID of "+id);
    }

    @Override
    public CommentResponse findById(String id) throws NotFoundException {

        if(commentRepository.findById(id).isPresent()) {
            Comment comment = commentRepository.findById(id).get();
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            User user = userRepository.findById(comment.getAuthor()).orElseThrow(() -> new NotFoundException("No user found for ID of "+comment.getAuthor()));
            UserResponse author = modelMapper.map(user, UserResponse.class);
            commentResponse.setAuthor(author);
            return commentResponse;
        }
        throw new NotFoundException("No comment found with ID of "+id);
    }


    @Override
    public List<CommentResponse> findAll() throws NotFoundException {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment: comments) {
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            User user = userRepository.findById(comment.getAuthor()).orElseThrow(() -> new NotFoundException("No user found for ID of "+comment.getAuthor()));
            UserResponse author = modelMapper.map(user, UserResponse.class);
            commentResponse.setAuthor(author);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public List<CommentResponse> findByArticle(String article) throws NotFoundException {

        List<Comment> comments = commentRepository.findByArticle(article);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment: comments) {
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            User user = userRepository.findById(comment.getAuthor()).orElseThrow(() -> new NotFoundException("No user found for ID of "+comment.getAuthor()));
            UserResponse author = modelMapper.map(user, UserResponse.class);
            commentResponse.setAuthor(author);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }
}
