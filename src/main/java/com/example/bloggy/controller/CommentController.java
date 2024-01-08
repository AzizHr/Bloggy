package com.example.bloggy.controller;

import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.Comment;
import com.example.bloggy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody Comment comment) throws NotFoundException {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponse> edit(@RequestBody Comment comment) throws NotFoundException {
        return new ResponseEntity<>(commentService.update(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable String id) throws NotFoundException {
        commentService.delete(id);
        return new ResponseEntity<>(Map.of("message", "Comment was removed with success"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> comments() throws NotFoundException {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/f")
    public ResponseEntity<List<CommentResponse>> articleComments(@RequestParam String articleId) throws NotFoundException {
        return new ResponseEntity<>(commentService.findByArticleId(articleId), HttpStatus.OK);
    }

}
