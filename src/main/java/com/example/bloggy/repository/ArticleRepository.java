package com.example.bloggy.repository;

import com.example.bloggy.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    Page<Article> findByAuthorId(String authorId, Pageable pageable);
    Page<Article> findByTitleIsLike(String title, Pageable pageable);
    Page<Article> findByContentIsLike(String content, Pageable pageable);
    @Query("{tags: ?0}")
    Page<Article> findByTag(String tag, Pageable pageable);

}
