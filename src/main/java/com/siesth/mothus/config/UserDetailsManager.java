package com.siesth.mothus.config;

import com.siesth.mothus.model.User;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserDetailsManager implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            user = userRepo.findUserByMail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
        }
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
