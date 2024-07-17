package com.universe.posts.application.services;

import com.universe.posts.domain.model.Post;
import com.universe.posts.domain.ports.in.PostUseCases;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements PostUseCases {

    private PostUseCases postUseCases;

    public PostService(PostUseCases postUseCases){
        this.postUseCases = postUseCases;
    }

    @Override
    public Post createPost(Post post) {
        return postUseCases.createPost(post);
    }

    @Override
    public void deletePost(Long id) {
        postUseCases.deletePost(id);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        return postUseCases.updatePost(id, post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postUseCases.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postUseCases.findAll();
    }

    @Override
    public List<Post> findByUser(UserEntity userEntity) {
        return postUseCases.findByUser(userEntity);
    }
}
