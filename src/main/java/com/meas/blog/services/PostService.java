package com.meas.blog.services;

import com.meas.blog.models.Post;
import com.meas.blog.models.dao.PostDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostDAO postDAO;

    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public List<Post> getPosts(){
        return postDAO.findAll();
    }
}
