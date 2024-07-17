package com.universe.usersSecurity.controller.dto;

import com.universe.usersSecurity.persistence.entity.RoleEntity;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDto(
     String username,
     Set<RoleEntity> roles
) {
}
