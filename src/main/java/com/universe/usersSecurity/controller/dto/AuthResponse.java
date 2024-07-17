package com.universe.usersSecurity.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.universe.usersSecurity.persistence.entity.RoleEntity;

import java.util.Set;

@JsonPropertyOrder({"username", "message", "status", "jwt", "id", "roles"})
public record AuthResponse(
        String username,
        String message,
        String jwt,
        Boolean status,
        Long id,
        Set<RoleEntity> roles)
{
}
