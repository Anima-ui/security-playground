package com.security.playground.restControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @GetMapping("/get")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("User");
    }
}
