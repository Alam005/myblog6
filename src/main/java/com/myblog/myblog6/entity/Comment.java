package com.myblog.myblog6.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String text;
   private String email;

   //Many comments can belong to one post
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

}
