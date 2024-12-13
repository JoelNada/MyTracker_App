package com.enterprise.tracker.app.service;

import com.enterprise.tracker.app.model.dto.AuthLoginDTO;
import com.enterprise.tracker.app.model.dto.AuthRegisterDTO;
import com.enterprise.tracker.app.model.dto.AuthResponseDTO;

public interface AuthService {
    public String registerUser(AuthRegisterDTO authRegisterDTO);
    public AuthResponseDTO login(AuthLoginDTO authLoginDTO);
    public String registerAdmin(AuthRegisterDTO authRegisterDTO);
}
