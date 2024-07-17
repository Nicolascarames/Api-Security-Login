package com.universe.usersSecurity.controller;

import com.universe.usersSecurity.controller.dto.UserDto;
import com.universe.usersSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rootAuth")
public class AuthenticationRootController {

    @Autowired
    private UserService userService;

    @PostMapping("/modUserByRoot")
    public ResponseEntity<?> modUserByRoot(@RequestParam("username") String username, @RequestBody List<String> roles){

        UserDto userDtoNew = userService.modUserByRoot(username, roles);

        return ResponseEntity.ok(userDtoNew);

    }

    @PostMapping("/modUserBy")
    public ResponseEntity<?> modUserBy(@RequestParam("username") String username, @RequestBody List<String> roles){



        return ResponseEntity.ok(username + roles);

    }

}
