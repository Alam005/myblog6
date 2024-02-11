package com.myblog.myblog6.controller;

import com.myblog.myblog6.payload.PostDto;
import com.myblog.myblog6.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
private PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

 //http://localhost:8080/api/posts/dto
    @PreAuthorize("hasRole('ADMIN')") //only an admin can create a post
    @PostMapping("/dto")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/getPost")
    public ResponseEntity<PostDto> getPostById(@RequestParam long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/all")
        public List<PostDto> getAllPost(
                @RequestParam(name="pageNo",required = false,defaultValue = "0") int pageNo,
                @RequestParam(name ="pageSize", required = false, defaultValue = "2") int pageSize,
                @RequestParam(name ="sortBy",required = false,defaultValue = "id") String sortBy,
                @RequestParam(name ="sortDir", required = false, defaultValue = "id") String sortDir
                )
    {
            List<PostDto> postDtos = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
            return postDtos;
        }

        //http://localhost:8080/api/posts/2
        @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("The post is Deleted!", HttpStatus.OK);
        }

        //http://localhost:8080/api/posts/update/1
@PutMapping("/update/{id}")
public ResponseEntity<PostDto> updatePostById(@PathVariable long id, @RequestBody PostDto postDto){
    PostDto updatedDto = postService.updatePostById(id,postDto);
    return new ResponseEntity<>(updatedDto, HttpStatus.OK);
}

}
