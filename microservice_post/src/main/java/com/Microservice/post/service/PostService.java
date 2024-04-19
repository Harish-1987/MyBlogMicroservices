package com.Microservice.post.service;

import com.Microservice.post.config.RestTemplateConfig;
import com.Microservice.post.entity.Post;
import com.Microservice.post.exception.ResourceNotFoundException;
import com.Microservice.post.payload.Comment;
import com.Microservice.post.payload.PostDto;
import com.Microservice.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();                //  These two lines are another way to generate random Id and assign to the post record
        post.setId(postId);                                          //
        return postRepository.save(post);
    }

    public Post findPostById(String id){
        Post foundPost = postRepository.findById(id).get();               //get() method will get us post from Optional<Post>
        return foundPost;
    }

    public PostDto getPostWithComments(String postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Record with given id is no present" + postId));
        if(post!=null) {
            ArrayList comments = restTemplate.getRestTemplate().getForObject("http://localhost:8082/api/comments/" + postId, ArrayList.class);
            //ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);

            PostDto postDto = new PostDto();
            postDto.setPostId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setDescription(post.getDescription());
            postDto.setComments(comments);

            return postDto;


        }else{
            return null;
        }


    }
}
