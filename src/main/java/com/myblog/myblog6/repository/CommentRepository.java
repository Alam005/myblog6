package com.myblog.myblog6.repository;

import com.myblog.myblog6.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
