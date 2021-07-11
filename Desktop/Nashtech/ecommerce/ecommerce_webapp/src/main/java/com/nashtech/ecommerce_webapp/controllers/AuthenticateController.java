package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.jwt.JwtTokenProvider;
import com.nashtech.ecommerce_webapp.jwt.details.UserDetailsImpl;
import com.nashtech.ecommerce_webapp.payload.LoginRequest;
import com.nashtech.ecommerce_webapp.payload.LoginResponse;
import com.nashtech.ecommerce_webapp.payload.RegisterRequest;
import com.nashtech.ecommerce_webapp.repositories.AccountRepository;
import com.nashtech.ecommerce_webapp.repositories.RoleRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthenticateController {

    private final  AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final AccountRepository accountRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;


    public AuthenticateController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, AccountRepository accountRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.accountRepository = accountRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

//    public ResponseEntity<?> registerAccount(@Validated @RequestBody RegisterRequest registerRequest){
//
//    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        //call username and password authentication token
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        //let authenticationManger authenticate that token -> return authenticate object
        Authentication authentication = authenticationManager.authenticate(authToken);
        if (authentication.isAuthenticated()){
            //if token is authenticated, set it to Security Context Holder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //then return jwt token to user
            String jwtToken = tokenProvider.generateToken(authentication);
            //as well return user email and password from User Details obj too
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            LoginResponse responseObj = new LoginResponse(
                    userDetails.getEmail(),
                    userDetails.getFullName(),
                    jwtToken
            );

            return new ResponseEntity<>(responseObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }

}
