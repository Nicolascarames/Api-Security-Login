package com.universe.usersSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
// de primeras rechazar todos los metodos
//@PreAuthorize("denyAll()")
public class TestController {

    @GetMapping("/get")
    public String callGet(){
        return "Method Called With GET";
    }

    @PostMapping("/post")
    public String callPost(){
        return "Method Called With POST";
    }

    @PutMapping("/put")
    public String callPut(){
        return "Method Called With PUT";
    }

    @DeleteMapping("/delete")
    public String callDelete(){
        return "Method Called With DELETE";
    }
}
