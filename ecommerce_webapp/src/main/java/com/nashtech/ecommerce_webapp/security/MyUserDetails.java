package com.nashtech.ecommerce_webapp.security;

import com.nashtech.ecommerce_webapp.models.user.User;
import com.nashtech.ecommerce_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User: " + email + " not found");
        }

        return UserDetailsImpl.build(user);
    }
}
