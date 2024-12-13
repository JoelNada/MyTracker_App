package com.enterprise.tracker.app.cotroller;

import com.enterprise.tracker.app.model.entity.User;
import com.enterprise.tracker.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public ResponseEntity<List<User>>test(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/getUser")
    public ResponseEntity<User>getUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(userRepository.findByUsername(userDetails.getUsername()));
    }
}
