package com.meas.blog.api.controllers.post;

import com.meas.blog.api.dtos.PostDTO;
import com.meas.blog.models.Post;
import com.meas.blog.models.User;
import com.meas.blog.services.PostService;
import com.meas.blog.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private UserService userService;
    private PostService postService;

    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDTO postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get-a-post/{id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable @RequestBody Long id) {

        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

}
