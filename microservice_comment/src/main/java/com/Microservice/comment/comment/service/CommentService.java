package com.Microservice.comment.comment.service;

import com.Microservice.comment.comment.config.RestTemplateConfig;
import com.Microservice.comment.comment.entity.Comment;
import com.Microservice.comment.comment.payload.Post;
import com.Microservice.comment.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplateConfig restTemplate;                        //But how to interact post microservices from this comment microservices in order to identify weather post with given id is present or not

    public Comment saveComment(Comment comment){
       Post post = restTemplate.getRestTemplate().getForObject("http://localhost:8081/api/posts/"+comment.getPostId(), Post.class);   //it will return content of post which must be stored in Post object only for that we created Post class in payload folder.

        if(post!=null) {                                                                  //   Before saving comment first we have to check weather post for which comment is saved is present or not
             String id = UUID.randomUUID().toString();
             comment.setCommentId(id);
             return commentRepository.save(comment);
        }else{
            return null;
        }
    }

    public List<Comment> getAllCommentByPostId(String postId){
        Post post = restTemplate.getRestTemplate().getForObject("http://localhost:8081/api/posts/"+postId, Post.class);    //Before registering with euraka server
        //Post post = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/"+postId, Post.class);
        if(post!=null) {                                                                  //   Before saving comment first we have to check weather post for which comment is saved is present or not
            return commentRepository.findByPostId(postId);
        }else{
            return null;
        }
    }

}


//Now I have to make this comment microservice/project intract with this url ->   //http://localhost:8081/api/posts/{postId}
// and search with postId weather Post is exist or not how do we do it?
// This where we use "rest template class"
