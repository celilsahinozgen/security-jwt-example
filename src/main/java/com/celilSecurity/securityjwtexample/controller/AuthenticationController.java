package com.celilSecurity.securityjwtexample.controller;


import com.celilSecurity.securityjwtexample.dto.UserDto;
import com.celilSecurity.securityjwtexample.dto.UserRequest;
import com.celilSecurity.securityjwtexample.dto.UserResponse;

import com.celilSecurity.securityjwtexample.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/save")
    public ResponseEntity<UserResponse> save(@RequestBody UserDto userDto ) {
            return ResponseEntity.ok(authenticationService.save(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponse>  auth(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authenticationService.auth(userRequest));

    }
}
