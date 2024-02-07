package com.myblog.myblog6.repository;

import com.myblog.myblog6.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
