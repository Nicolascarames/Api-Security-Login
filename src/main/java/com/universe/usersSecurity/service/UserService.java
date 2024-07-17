package com.universe.usersSecurity.service;

import com.universe.usersSecurity.controller.dto.UserDto;
import com.universe.usersSecurity.persistence.entity.RoleEntity;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import com.universe.usersSecurity.persistence.repository.RoleRepository;
import com.universe.usersSecurity.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserDto modUserByRoot (String username, List<String> rolesRequest){

        List<String> rolesRequestNew = new ArrayList<>();
        rolesRequest.stream().map(rolesRequestNew::add);

        // buscamos si los roles enviados coinciden con los de la bd
        Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest).stream().collect(Collectors.toSet());

        // si no hay ningun role , enviamos un error
        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntityOptional = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        userEntityOptional.setRoles(roleEntityList);

        UserEntity userSaved = userRepository.save(userEntityOptional);

        UserDto userDto = UserDto.builder()
                .username(userSaved.getUsername())
                .roles(userSaved.getRoles())
                .build();

        return userDto;

    }
}




