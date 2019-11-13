package com.dajian.vhrserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegAndLoginController {

    @GetMapping("/login_p")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("尚未登陆，请登录");
    }
}
