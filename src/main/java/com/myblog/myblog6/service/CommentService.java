package com.myblog.myblog6.service;

import com.myblog.myblog6.entity.Comment;
import com.myblog.myblog6.payload.CommentDto;

public interface CommentService {
    void deleteCommentById(long id);


    CommentDto createComment(CommentDto commentDto, long postId);

    CommentDto updateComment(long id, CommentDto commentDto);

   CommentDto mapToDto(Comment comment);
   Comment mapToEntity(CommentDto commentDto);
}
