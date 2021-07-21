package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.dtos.SigninRequestDTO;
import com.nashtech.ecommerce_webapp.dtos.SigninResponseDTO;
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


@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDTO> login(@RequestParam String username, @RequestParam String password){
        System.out.println("login() is invoked - UserController");
        SigninResponseDTO dto =  service.signIn(username, password);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SigninRequestDTO dto){
        return service.signUp(modelMapper.map(dto, User.class));
    }



    @GetMapping(value = "/accounts")
    public ResponseEntity<List<User>> getAccounts(){
        List<User> users = service.getAll();
        if (users.size()>0) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<User> viewMyProfile(){
        User user = service.whoAmI()
    }

    //get user by email
    @GetMapping(value = "/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getAccountByEmail(@PathParam("email") String email){
        User result = service.getAccountByEmail(email);
        return result != null
               ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/search/name")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAccountsLikeName(@PathParam("searchValue") String searchValue){
        List<User> accounts = service.getAccountsLikeName(searchValue);
        return accounts != null
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//    @PutMapping
//    public ResponseEntity<Boolean> updateAccount(@RequestBody User account){
//        boolean result = service.updateAccount(account);
//        return result
//                ? new ResponseEntity<>(true, HttpStatus.OK)
//                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("email") String email){
        boolean result = service.deleteAccount(email);
        return result
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }





}
