package com.myblog.myblog6.service;

import com.myblog.myblog6.entity.Post;
import com.myblog.myblog6.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

   Post mapToEntity(PostDto postDto);
   PostDto mapToDto(Post post);

    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
