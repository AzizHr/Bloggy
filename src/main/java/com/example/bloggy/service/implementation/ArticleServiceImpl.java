package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.article.ArticleDTO;
import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Article;
import com.example.bloggy.model.Comment;
import com.example.bloggy.model.User;
import com.example.bloggy.model.Media;
import com.example.bloggy.repository.ArticleRepository;
import com.example.bloggy.repository.CommentRepository;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              UserRepository userRepository,
                              CommentRepository commentRepository,
                              ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleResponse save(ArticleDTO articleDTO) throws NotFoundException {
        Article article = modelMapper.map(articleDTO, Article.class);

        if(userRepository.findById(articleDTO.getAuthorId()).isPresent()) {
            article.setAuthorId(article.getAuthorId());
            article.setCreatedAt(LocalDateTime.now());
            List<Media> medias = articleDTO.getMedias();
            article.setMedias(medias);

            return modelMapper.map(articleRepository.save(article), ArticleResponse.class);
        }
        throw new NotFoundException("No user found with ID of "+articleDTO.getAuthorId());

    }

    @Override
    public ArticleResponse update(ArticleDTO articleDTO) throws NotFoundException {

        if(articleRepository.findById(articleDTO.getId()).isPresent()) {

            Article article = modelMapper.map(articleDTO, Article.class);

            if(userRepository.findById(articleDTO.getAuthorId()).isPresent()) {
                article.setAuthorId(article.getAuthorId());

                List<Media> medias = articleDTO.getMedias();
                article.setMedias(medias);
                article.setCreatedAt(article.getCreatedAt());

                return modelMapper.map(articleRepository.save(article), ArticleResponse.class);
            }
            throw new NotFoundException("No user found with ID of "+articleDTO.getAuthorId());

        }
        throw new NotFoundException("No article was found with ID of "+articleDTO.getId());
    }

    @Override
    public void delete(String id) throws NotFoundException {
        if(articleRepository.findById(id).isPresent()) {
            articleRepository.deleteById(id);
        }
        throw new NotFoundException("No article was found with ID of "+id);
    }

    @Override
    public ArticleResponse findById(String id) throws NotFoundException {

        if(articleRepository.findById(id).isPresent()) {
            Article article = articleRepository.findById(id).get();

            if(userRepository.findById(article.getAuthorId()).isPresent()) {
                User author = userRepository.findById(article.getAuthorId()).get();
                ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);
                UserResponse userResponse = modelMapper.map(author, UserResponse.class);
                articleResponse.setAuthor(userResponse);

                List<Comment> comments = commentRepository.findByArticleId(article.getId());
                List<CommentResponse> commentResponses = new ArrayList<>();

                for(Comment comment: comments) {
                    CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                    if(userRepository.findById(comment.getArticleId()).isPresent()) {
                        User commentUser = userRepository.findById(comment.getArticleId()).get();
                        UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);

                        commentResponse.setAuthor(commentAuthor);
                    } else {
                        commentResponse.setAuthor(null);
                    }
                    commentResponses.add(commentResponse);
                }
                articleResponse.setComments(commentResponses);
                return articleResponse;
            }
            throw new NotFoundException("No author was found with ID of "+article.getAuthorId());
        }
        throw new NotFoundException("No article was found with ID of "+id);
    }

    @Override
    public Page<ArticleResponse> findAll(Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findAll(pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            User user = userRepository.findById(article.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthorId()));

            UserResponse articleAuthor = modelMapper.map(user, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticleId(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                if(userRepository.findById(comment.getArticleId()).isPresent()) {
                    User commentUser = userRepository.findById(comment.getArticleId()).get();
                    UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);

                    commentResponse.setAuthor(commentAuthor);
                } else {
                    commentResponse.setAuthor(null);
                }

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }


    @Override
    public Page<ArticleResponse> findByAuthorId(String authorId, Pageable pageable) throws NotFoundException {

        if(userRepository.findById(authorId).isPresent()) {
            Page<Article> articlePage = articleRepository.findByAuthorId(authorId, pageable);
            List<ArticleResponse> articleResponses = new ArrayList<>();

            for (Article article : articlePage.getContent()) {
                ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

                User user = userRepository.findById(article.getAuthorId())
                        .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthorId()));

                UserResponse articleAuthor = modelMapper.map(user, UserResponse.class);
                articleResponse.setAuthor(articleAuthor);

                List<Comment> comments = commentRepository.findByArticleId(article.getId());
                List<CommentResponse> commentResponses = new ArrayList<>();

                for (Comment comment : comments) {
                    CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                    if(userRepository.findById(comment.getArticleId()).isPresent()) {
                        User commentUser = userRepository.findById(comment.getArticleId()).get();
                        UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);

                        commentResponse.setAuthor(commentAuthor);
                    } else {
                        commentResponse.setAuthor(null);
                    }

                    commentResponses.add(commentResponse);
                }

                articleResponse.setComments(commentResponses);
                articleResponses.add(articleResponse);
            }

            return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
        }
        throw new NotFoundException("No user found with ID of "+authorId);
    }

    @Override
    public Page<ArticleResponse> searchByTitle(String title, Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findByTitleIsLike(title, pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            User user = userRepository.findById(article.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthorId()));

            UserResponse articleAuthor = modelMapper.map(user, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticleId(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                User commentUser = userRepository.findById(comment.getAuthorId()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<ArticleResponse> searchByContent(String content, Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findByContentIsLike(content, pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            User user = userRepository.findById(article.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthorId()));

            UserResponse articleAuthor = modelMapper.map(user, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticleId(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                User commentUser = userRepository.findById(comment.getAuthorId()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<ArticleResponse> findByTag(String tag, Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findByTag(tag, pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            User user = userRepository.findById(article.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthorId()));

            UserResponse articleAuthor = modelMapper.map(user, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticleId(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                User commentUser = userRepository.findById(comment.getAuthorId()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }

}