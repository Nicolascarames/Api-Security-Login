package com.universe.posts.infrastructure.repository;

import com.universe.posts.domain.model.Post;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    List<Post> findByUser(Long user_id);

    List<Post> findByUser(UserEntity userEntity);
}
