package com.meas.blog.services;

import com.meas.blog.api.dtos.PostDTO;
import com.meas.blog.exceptions.PostNotFoundException;
import com.meas.blog.models.Post;
import com.meas.blog.models.User;
import com.meas.blog.models.dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserService userService;

    public PostService(PostDAO postDAO, UserService userService) {
        this.postDAO = postDAO;
        this.userService = userService;
    }

    public List<Post> getPosts(){
        return postDAO.findAll();
    }

    @Transactional
    public void createPost(PostDTO postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUsername(postDto.getUsername());
        User loggedInUser = userService.getCurrentUser(postDto.getUsername());
        post.setCreatedOn(Instant.now());
        post.setUser(loggedInUser);
        post.setUpdatedOn(Instant.now());
        postDAO.save(post);
    }

    @Transactional
    public PostDTO readSinglePost(Long id) {
        Post post = postDAO.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUser().getUsername());
        return postDto;
    }

}
