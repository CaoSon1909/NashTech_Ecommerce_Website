package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.dtos.SigninRequestDTO;
import com.nashtech.ecommerce_webapp.dtos.SigninResponseDTO;
import com.nashtech.ecommerce_webapp.dtos.SignupRequestDTO;
import com.nashtech.ecommerce_webapp.dtos.SignupResponseDTO;
import com.nashtech.ecommerce_webapp.models.user.User;
import com.nashtech.ecommerce_webapp.services.UserService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/public/signin")
    public ResponseEntity<SigninResponseDTO> login(@RequestBody SigninRequestDTO dto){
        SigninResponseDTO res =  service.signIn(dto.getEmail(), dto.getPassword());
        return res != null ? new ResponseEntity<>(res, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/public/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO dto){
        User user = service.signUp(dto);
        if (user != null){
            return new ResponseEntity<>("Sign up success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Sign up fail", HttpStatus.NOT_FOUND);
    }



    @GetMapping(value = "/api/v1/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAccounts(){
        List<User> users = service.getAll();
        if (users.size()>0) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //get user by email
    @GetMapping(value = "/public/search")
    public ResponseEntity<User> getAccountByEmail(@PathParam("email") String email){
        User result = service.getAccountByEmail(email);
        return result != null
               ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "api/v1/users/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAccountsLikeName(@PathParam("searchValue") String searchValue){
        List<User> accounts = service.getAccountsLikeName(searchValue);
        return accounts != null
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/v1/users/update")
    public ResponseEntity<Boolean> updateAccount(@RequestBody User account){
        boolean result = service.updateAccount(account);
        return result
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/v1/users/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("email") String email){
        boolean result = service.deleteAccount(email);
        return result
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }





}
