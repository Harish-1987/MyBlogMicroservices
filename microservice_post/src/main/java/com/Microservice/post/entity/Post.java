package com.Microservice.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="posts")
public class Post {

    @Id
    private String id;

    @Column(name="Title", nullable=false)
    private String title;

    @Column(name="Description", nullable = false)
    private String description;

    @Column(name="content", nullable = false)
    private String content;

}
