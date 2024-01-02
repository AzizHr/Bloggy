package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.article.ArticleDTO;
import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Article;
import com.example.bloggy.model.Comment;
import com.example.bloggy.model.CustomUser;
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

        if(userRepository.findById(articleDTO.getAuthor()).isPresent()) {
            article.setAuthor(article.getAuthor());
            article.setCreatedAt(LocalDateTime.now());
            List<Media> medias = articleDTO.getMedias();
            article.setMedias(medias);

            return modelMapper.map(articleRepository.save(article), ArticleResponse.class);
        }
        throw new NotFoundException("No user found with ID of "+articleDTO.getAuthor());

    }

    @Override
    public ArticleResponse update(ArticleDTO articleDTO) throws NotFoundException {

        if(articleRepository.findById(articleDTO.getId()).isPresent()) {

            Article article = modelMapper.map(articleDTO, Article.class);

            if(userRepository.findById(articleDTO.getAuthor()).isPresent()) {
                article.setAuthor(article.getAuthor());

                List<Media> medias = articleDTO.getMedias();
                article.setMedias(medias);

                return modelMapper.map(articleRepository.save(article), ArticleResponse.class);
            }
            throw new NotFoundException("No user found with ID of "+articleDTO.getAuthor());

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
        return modelMapper
                .map(articleRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("No article was found with ID of "+id)),
                        ArticleResponse.class);
    }

    @Override
    public Page<ArticleResponse> findAll(Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findAll(pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            CustomUser customUser = userRepository.findById(article.getAuthor())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthor()));

            UserResponse articleAuthor = modelMapper.map(customUser, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticle(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                CustomUser commentCustomUser = userRepository.findById(comment.getAuthor()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentCustomUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }


    @Override
    public Page<ArticleResponse> findByAuthor(String author, Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findByAuthor(author, pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            CustomUser customUser = userRepository.findById(article.getAuthor())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthor()));

            UserResponse articleAuthor = modelMapper.map(customUser, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticle(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                CustomUser commentCustomUser = userRepository.findById(comment.getAuthor()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentCustomUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }

    @Override
    public Page<ArticleResponse> searchByTitle(String title, Pageable pageable) throws NotFoundException {
        Page<Article> articlePage = articleRepository.findByTitleIsLike(title, pageable);
        List<ArticleResponse> articleResponses = new ArrayList<>();

        for (Article article : articlePage.getContent()) {
            ArticleResponse articleResponse = modelMapper.map(article, ArticleResponse.class);

            CustomUser customUser = userRepository.findById(article.getAuthor())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthor()));

            UserResponse articleAuthor = modelMapper.map(customUser, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticle(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                CustomUser commentCustomUser = userRepository.findById(comment.getAuthor()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentCustomUser, UserResponse.class);
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

            CustomUser customUser = userRepository.findById(article.getAuthor())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthor()));

            UserResponse articleAuthor = modelMapper.map(customUser, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticle(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                CustomUser commentCustomUser = userRepository.findById(comment.getAuthor()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentCustomUser, UserResponse.class);
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

            CustomUser customUser = userRepository.findById(article.getAuthor())
                    .orElseThrow(() -> new NotFoundException("No user found for ID of " + article.getAuthor()));

            UserResponse articleAuthor = modelMapper.map(customUser, UserResponse.class);
            articleResponse.setAuthor(articleAuthor);

            List<Comment> comments = commentRepository.findByArticle(article.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

                CustomUser commentCustomUser = userRepository.findById(comment.getAuthor()).orElse(null);
                UserResponse commentAuthor = modelMapper.map(commentCustomUser, UserResponse.class);
                commentResponse.setAuthor(commentAuthor);

                commentResponses.add(commentResponse);
            }

            articleResponse.setComments(commentResponses);
            articleResponses.add(articleResponse);
        }

        return new PageImpl<>(articleResponses, pageable, articlePage.getTotalElements());
    }

}