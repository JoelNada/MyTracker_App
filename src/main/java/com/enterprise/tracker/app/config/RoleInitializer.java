package com.enterprise.tracker.app.config;

import com.enterprise.tracker.app.model.entity.Role;
import com.enterprise.tracker.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        for (String roleName : roles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                roleRepository.save(role);
                System.out.println("Role " + roleName + " created.");
            }
        }
    }
}
