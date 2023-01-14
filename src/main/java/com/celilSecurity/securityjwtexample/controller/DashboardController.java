package com.celilSecurity.securityjwtexample.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anasayfa")
public class DashboardController {

    @GetMapping
    public ResponseEntity<String> anasayfa () {
        return ResponseEntity.ok("Anasayfaaa....");
    }
}
