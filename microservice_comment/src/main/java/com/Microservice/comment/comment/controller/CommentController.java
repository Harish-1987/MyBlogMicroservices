package com.Microservice.comment.comment.controller;

import com.Microservice.comment.comment.entity.Comment;
import com.Microservice.comment.comment.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/comments")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    //  http://localhost:8082/api/comments
    @PostMapping()
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){

        Comment comment1 = commentService.saveComment(comment);
        return new ResponseEntity<>(comment1, HttpStatus.OK);
    }

    //  http://localhost:8082/api/comments/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentByPostId(@PathVariable String postId){
        List<Comment> comments = commentService.getAllCommentByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


}


//