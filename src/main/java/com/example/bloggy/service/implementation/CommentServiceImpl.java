package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Article;
import com.example.bloggy.model.Comment;
import com.example.bloggy.model.CustomUser;
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
    public CommentResponse save(Comment comment) throws NotFoundException {

        if(articleRepository.findById(comment.getAuthorId()).isPresent()) {
            Article article = articleRepository.findById(comment.getArticleId()).get();
            CustomUser customUser = userRepository.findById(comment.getAuthorId()).get();

            comment.setArticleId(article.getId());
            comment.setAuthorId(customUser.getId());
            comment.setCreatedAt(LocalDateTime.now());

            commentRepository.save(comment);
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            commentResponse.setAuthor(modelMapper.map(customUser, UserResponse.class));
//            article.setComments(List.of(comment));
            return commentResponse;
        }
        throw new NotFoundException("No article was found with ID of "+ comment.getArticleId());

    }

    @Override
    public CommentResponse update(Comment comment) throws NotFoundException {

        if(articleRepository.findById(comment.getAuthorId()).isPresent()) {
            Article article = articleRepository.findById(comment.getArticleId()).get();
             CustomUser customUser = userRepository.findById(comment.getAuthorId()).get();

             comment.setArticleId(article.getId());
             comment.setAuthorId(customUser.getId());

             commentRepository.save(comment);
             CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
             commentResponse.setAuthor(modelMapper.map(customUser, UserResponse.class));
//             article.setComments(List.of(comment));
             return commentResponse;
        }
        throw new NotFoundException("No article was found with ID of "+ comment.getArticleId());

    }

    @Override
    public void delete(String id) throws NotFoundException {
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
            CustomUser customUser = userRepository.findById(comment.getAuthorId()).orElseThrow(() -> new NotFoundException("No user found for ID of "+comment.getAuthorId()));
            UserResponse author = modelMapper.map(customUser, UserResponse.class);
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
            CustomUser customUser = userRepository.findById(comment.getAuthorId()).orElseThrow(() -> new NotFoundException("No customUser found for ID of "+comment.getAuthorId()));
            UserResponse author = modelMapper.map(customUser, UserResponse.class);
            commentResponse.setAuthor(author);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public List<CommentResponse> findByArticleId(String articleId) throws NotFoundException {

        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment: comments) {
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
            CustomUser customUser = userRepository.findById(comment.getAuthorId()).orElseThrow(() -> new NotFoundException("No customUser found for ID of "+comment.getAuthorId()));
            UserResponse author = modelMapper.map(customUser, UserResponse.class);
            commentResponse.setAuthor(author);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }
}
