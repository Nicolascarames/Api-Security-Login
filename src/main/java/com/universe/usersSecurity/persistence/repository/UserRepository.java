package com.universe.usersSecurity.persistence.repository;

import com.universe.usersSecurity.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    UserEntity findUserEntityById(Long id) ;

}
