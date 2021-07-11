package com.nashtech.ecommerce_webapp.jwt.details;

import com.nashtech.ecommerce_webapp.models.Account;
import com.nashtech.ecommerce_webapp.repositories.AccountRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findById(email).get();
        if (account == null){
            throw new UsernameNotFoundException("User email not found : " + email);
        }
        return UserDetailsImpl.build(account);
    }
}
