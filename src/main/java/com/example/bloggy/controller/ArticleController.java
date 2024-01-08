package com.example.bloggy.controller;

import com.example.bloggy.dto.article.ArticleDTO;
import com.example.bloggy.dto.article.ArticleResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> create(@RequestBody ArticleDTO articleDTO) throws NotFoundException {
        return new ResponseEntity<>(articleService.save(articleDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ArticleResponse> edit(@RequestBody ArticleDTO articleDTO) throws NotFoundException {
        return new ResponseEntity<>(articleService.update(articleDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable String id) throws NotFoundException {
        articleService.delete(id);
        return new ResponseEntity<>(Map.of("message", "Article was removed with success"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> articles(Pageable pageable) throws NotFoundException {
        if(articleService.findAll(pageable).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No articles found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> article(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam String title, Pageable pageable) throws NotFoundException {
        if(articleService.searchByTitle(title, pageable).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No articles found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.searchByTitle(title, pageable), HttpStatus.OK);
    }

    @GetMapping("/s")
    public ResponseEntity<?> searchByContent(@RequestParam String content, Pageable pageable) throws NotFoundException {
        if(articleService.searchByContent(content, pageable).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No articles found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.searchByContent(content, pageable), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterByTag(@RequestParam String tag, Pageable pageable) throws NotFoundException {
        if(articleService.findByTag(tag, pageable).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No articles found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.findByTag(tag, pageable), HttpStatus.OK);
    }

    @GetMapping("/g")
    public ResponseEntity<?> articlesByAuthorId(@RequestParam String authorId, Pageable pageable) throws NotFoundException {
        if(articleService.findByAuthorId(authorId, pageable).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "No articles found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.findByAuthorId(authorId, pageable), HttpStatus.OK);
    }

}
