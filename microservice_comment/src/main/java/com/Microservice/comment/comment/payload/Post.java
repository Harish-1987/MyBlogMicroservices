package com.Microservice.comment.comment.payload;

import lombok.Data;


@Data
public class Post {

    private String postId;

    private String title;

    private String description;

    private String content;
}
