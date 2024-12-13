package com.enterprise.tracker.app.service.serviceImpl;

import com.enterprise.tracker.app.exceptions.customExceptions.RoleNotFoundException;
import com.enterprise.tracker.app.exceptions.customExceptions.UserAlreadyExists;
import com.enterprise.tracker.app.model.dto.*;
import com.enterprise.tracker.app.model.entity.*;
import com.enterprise.tracker.app.repository.*;
import com.enterprise.tracker.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    public String registerUser(AuthRegisterDTO authRegisterDTO) {
        User checkUser = userRepository.findByUsername(authRegisterDTO.getUsername());
        if(checkUser!=null){
            throw new UserAlreadyExists("User already exists !!");
        }
        else{
            User user = new User();
            user.setEmail(authRegisterDTO.getEmail());
            user.setFirstname(authRegisterDTO.getFirstname());
            user.setLastname(authRegisterDTO.getLastname());
            user.setUsername(authRegisterDTO.getUsername());
            user.setPassword(passwordEncoder.encode(authRegisterDTO.getPassword()));
            Role userRole=roleRepository.findByName("ROLE_USER").orElseThrow(()-> new RoleNotFoundException("Role Not Found !!"));
            user.getRoles().add(userRole);
            userRepository.save(user);
            return "User Created";}

    }

    @Override
    public AuthResponseDTO login(AuthLoginDTO authLoginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(),
                        authLoginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            UserDetails userDetails = myUserDetailService.loadUserByUsername(authLoginDTO.getUsername());
            String token= jwtService.generateToken(userDetails);
            AuthResponseDTO response = new AuthResponseDTO();
            response.setToken(token);
            response.setUsername(jwtService.getUsernameFromToken(token));
            response.setRole(jwtService.extractRoles(token));
            return response;
        }
        else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public String registerAdmin(AuthRegisterDTO authRegisterDTO) {
        User checkUser = userRepository.findByUsername(authRegisterDTO.getUsername());
        if(checkUser!=null){
          throw new UserAlreadyExists("User already exists !!");
        }
        else{
            User user = new User();
            user.setEmail(authRegisterDTO.getEmail());
            user.setFirstname(authRegisterDTO.getFirstname());
            user.setLastname(authRegisterDTO.getLastname());
            user.setUsername(authRegisterDTO.getUsername());
            user.setPassword(passwordEncoder.encode(authRegisterDTO.getPassword()));
            Role userRole=roleRepository.findByName("ROLE_ADMIN").orElseThrow(()-> new RoleNotFoundException("Role Not Found !!"));
            user.getRoles().add(userRole);
            userRepository.save(user);
            return "User Created";
        }

    }
}
