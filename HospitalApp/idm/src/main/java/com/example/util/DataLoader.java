package com.example.util;

import com.example.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public void run(ApplicationArguments args) {
        try {
            userService.createRoles();
            userService.createAdministrator();
            System.out.println("Data loaded.");
        } catch (Exception e) {
            System.out.println("Data not loaded: " + e.getMessage());
        }

    }
}