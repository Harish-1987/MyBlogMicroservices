package com.Microservice.post.controller;

import com.Microservice.post.entity.Post;
import com.Microservice.post.payload.PostDto;
import com.Microservice.post.repository.PostRepository;
import com.Microservice.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/posts")
@RestController
public class PostController {

    @Autowired
    private PostService postService;



    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post){
        Post newPost = postService.savePost(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    //     http://localhost:8081/api/posts/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable String postId){
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }


    //     http://localhost:8081/api/posts/{postId}/comments

    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name="commentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
       PostDto postDto =  postService.getPostWithComments(postId);
       return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex){
        System.out.println("Fallback is execuated because server is down:"+ex.getMessage());
        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setPostId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");

        //I created a object of type PostDto and provided the dummy data and returned it back because the return type of commentFallback is same as on which method it is imposed on
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

}

//   getPostWithComments method from controller layer calling method of Service layer which is using restTemplate to
//  intracting with some other microservice here, with the help of @CircuitBreaker()   circuit breaker checks these calls are happening properly or not.
//  @CircuitBreaker(name="commentBreaker", fallbackMethod = "commentFallback")
//  name= "commentBreaker" is the name of circuit Breaker which is defined in application.yml file
//  fallbackMethod = "commentFallback" -> if the service is down it will call "commentFallback" method automatically and that method will return back the message "Service is down.".

