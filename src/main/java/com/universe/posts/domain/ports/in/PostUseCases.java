package com.universe.posts.domain.ports.in;

import com.universe.posts.domain.model.Post;
import com.universe.usersSecurity.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostUseCases {

    Post createPost (Post post);

    void deletePost (Long id);

    Post updatePost(Long id, Post post);

    Optional<Post> findById (Long id);

    List<Post> findAll();

    List<Post> findByUser(UserEntity userEntity);

}
