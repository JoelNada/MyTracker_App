package com.enterprise.tracker.app.cotroller;

import com.enterprise.tracker.app.model.dto.*;
import com.enterprise.tracker.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
public class Controller {

    @Autowired
    private AuthService authService;

    @PostMapping("/admin-register")
    public ResponseEntity<String> registerAdmin(@RequestBody AuthRegisterDTO registerDTO){
        String message = authService.registerAdmin(registerDTO);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody AuthRegisterDTO dto){
        String message = authService.registerUser(dto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthLoginDTO dto){
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}
