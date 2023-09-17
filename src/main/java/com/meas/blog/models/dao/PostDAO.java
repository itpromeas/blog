package com.meas.blog.models.dao;

import com.meas.blog.models.Post;
import org.springframework.data.repository.ListCrudRepository;

public interface PostDAO extends ListCrudRepository<Post, Long> {
}
