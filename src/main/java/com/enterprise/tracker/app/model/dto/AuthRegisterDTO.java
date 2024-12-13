package com.enterprise.tracker.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}
