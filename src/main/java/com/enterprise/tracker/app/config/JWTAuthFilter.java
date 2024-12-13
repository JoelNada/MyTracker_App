package com.enterprise.tracker.app.config;

import com.enterprise.tracker.app.exceptions.customExceptions.UnAuthorizedException;
import com.enterprise.tracker.app.model.entity.Role;
import com.enterprise.tracker.app.service.JWTService;
import com.enterprise.tracker.app.service.serviceImpl.MyUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JWTService jwtService;

    public JWTAuthFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.getUsernameFromToken(token);
            }
        } catch (SignatureException e) {
            logger.info("Invalid Token, Please Authenticate Again");
            request.setAttribute("error","Invalid Token, Please Authenticate Again");
            throw new UnAuthorizedException("Invalid Token, Please Authenticate Again"); // Custom message for EntryPoint
        } catch (ExpiredJwtException e) {
            logger.info("Token, Please Authenticate Again");
            request.setAttribute("error","Session Expired !!, Please Authenticate Again");
            throw new UnAuthorizedException("Token Expired, Please Authenticate Again !!");
        }
        catch (IllegalArgumentException e) {
            throw new UnAuthorizedException("JWT claims string is empty or invalid.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = applicationContext.getBean(MyUserDetailService.class).loadUserByUsername(username);
            Set<String> roles = jwtService.extractRoles(token);
            // Convert Set<String> roles to Set<GrantedAuthority>
            Set<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)  // Convert each role string to a SimpleGrantedAuthority
                    .collect(Collectors.toSet());
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
