package com.myblog.myblog6.service.impl;

import com.myblog.myblog6.entity.Post;
import com.myblog.myblog6.exception.ResourceNotFoundException;
import com.myblog.myblog6.payload.PostDto;
import com.myblog.myblog6.repository.PostRepository;
import com.myblog.myblog6.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
private PostRepository postRepository;
private ModelMapper modelMapper;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    @Override
    public PostDto mapToDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }



    @Override
    public List<PostDto> getAllPost(int pageNo, int  pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> listPost = pagePost.getContent();
        List<PostDto> listDtos = listPost.stream().map(n -> mapToDto(n)).collect(Collectors.toList());
        return listDtos;
    }


    @Override
    public void deletePostById(long id) {
        postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        postRepository.deleteById(id);

    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not not found with id: " + id));
        modelMapper.map(postDto,post);
        post.setId(id);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }


}
