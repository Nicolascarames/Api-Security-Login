package com.universe.posts.application.usecases;

import com.universe.posts.domain.model.Post;
import com.universe.posts.domain.ports.in.PostUseCases;
import com.universe.posts.infrastructure.repository.PostRepository;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostUseCasesImpl implements PostUseCases {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        post.setId(id);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByUser(UserEntity userEntity) {
        return postRepository.findByUser(userEntity);
    }

}
