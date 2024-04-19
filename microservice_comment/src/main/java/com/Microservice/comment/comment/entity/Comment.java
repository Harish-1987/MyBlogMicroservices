package com.Microservice.comment.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tables")
public class Comment {

    @Id
    private String commentId;
    private String name;
    private String email;
    private String body;
    private String postId;

}

