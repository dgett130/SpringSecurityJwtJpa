package com.testing.springsecuritytest.service;

import com.testing.springsecuritytest.domain.Role;
import com.testing.springsecuritytest.domain.User;
import com.testing.springsecuritytest.repository.RoleRepo;
import com.testing.springsecuritytest.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByUsername(username);
       if (user == null) {
           log.error("User not found in database");
           throw new UsernameNotFoundException("User not found in database");
       } else {
           log.info("User found in database, {}", user);
       }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       // AGGIUNGO I RUOLI ALL'UTENTE
       user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Salvataggio nuovo utente");
        user.setPassword(this.passwordEncoderBean().encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Salvataggio nuovo ruolo: {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Aggiungo il ruolo {} all'utente {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching utente {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetch di tutti gli utenti");
        return userRepo.findAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

}
