package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.commons.Constant;
import com.nashtech.ecommerce_webapp.models.Account;
import com.nashtech.ecommerce_webapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("api/v1/account")
@RestController
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    /**
     * @rest-controller: create account
     * @param: Account account
     *
     **/
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        return service.createAccount(account)
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAccounts(){
        List<Account> accounts = service.getAccounts();
        return !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all/{pageNo}/{pageSize}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize){
        List<Account> accounts = service.findAndPaginate(pageNo, pageSize);
        return !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/id/{email}")
    public ResponseEntity<Account> getAccountByID(@PathVariable("email") String email){
        Optional<Account> result = service.getAccountByEmail(email);
        return result
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/name/{fullname}")
    public ResponseEntity<List<Account>> getAccountsLikeName(@PathVariable("fullname") String fullName){
        List<Account> accounts = service.getAccountsLikeName(fullName);
        return accounts != null
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateAccount(@RequestBody Account account){
        boolean result = service.updateAccount(account);
        return result
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable("email") String email){
        boolean result = service.deleteAccount(email);
        return result
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }





}
