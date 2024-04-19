package com.Microservice.post.payload;

import lombok.Data;


@Data
public class Comment {
    private String commentId;
    private String name;
    private String email;
    private String body;
    private String postId;
}
