package com.myblog.myblog6.service.impl;

import com.myblog.myblog6.entity.Comment;
import com.myblog.myblog6.entity.Post;
import com.myblog.myblog6.exception.ResourceNotFoundException;
import com.myblog.myblog6.payload.CommentDto;
import com.myblog.myblog6.repository.CommentRepository;
import com.myblog.myblog6.repository.PostRepository;
import com.myblog.myblog6.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteCommentById(long id) {
        commentRepository.findById(id).orElseThrow(()->new RuntimeException("Comment not found with id: "+id)); //Returns comments from repository layer if present.
        //if not found the next line of code will not be executed further.
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with id: " + postId));
        Comment comment = new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post); //set comment for a particular post
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setEmail(savedComment.getEmail());
        dto.setText(savedComment.getText());

        return dto;
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment is not found with id: " + id));
        comment.setText(commentDto.getText()); //updating the comment here
        comment.setEmail(commentDto.getEmail()); //id =0, text ="...", email="..."
        Comment savedComment = commentRepository.save(comment);
        CommentDto updatedCommentDto = mapToDto(savedComment);
        return updatedCommentDto;
    }

    @Override
    public CommentDto mapToDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    @Override
    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
