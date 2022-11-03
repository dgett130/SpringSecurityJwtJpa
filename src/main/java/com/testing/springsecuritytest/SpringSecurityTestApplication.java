package com.testing.springsecuritytest;

import com.testing.springsecuritytest.domain.Role;
import com.testing.springsecuritytest.domain.User;
import com.testing.springsecuritytest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityTestApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            userService.saveRole(new Role(null, "manager"));

            userService.saveUser(new User(null, "Tim Burton","TIM11","1234",new ArrayList<>()));
            userService.saveUser(new User(null, "Giggi Proietti","giggi","diciotto",new ArrayList<>()));
            userService.saveUser(new User(null, "Paolo Bonolis","paolo","vadaa",new ArrayList<>()));
            userService.saveUser(new User(null, "Paolo Villaggio","fantozzi","fantocci",new ArrayList<>()));

            userService.addRoleToUser("TIM11", "ROLE_USER");
            userService.addRoleToUser("giggi", "ROLE_USER");
            userService.addRoleToUser("paolo", "manager");
            userService.addRoleToUser("fantozzi", "ROLE_ADMIN");
            userService.addRoleToUser("fantozzi", "ROLE_USER");
        };
    }

}
