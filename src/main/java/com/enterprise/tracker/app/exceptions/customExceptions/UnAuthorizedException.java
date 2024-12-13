package com.enterprise.tracker.app.exceptions.customExceptions;


import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class UnAuthorizedException extends AuthenticationException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
