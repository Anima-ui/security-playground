package com.security.playground.restControllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping("/get")
    public ResponseEntity<String> admin(HttpServletRequest request) {
        return new ResponseEntity<>("User" + request.getRequestURI(), HttpStatus.OK);
    }
}
