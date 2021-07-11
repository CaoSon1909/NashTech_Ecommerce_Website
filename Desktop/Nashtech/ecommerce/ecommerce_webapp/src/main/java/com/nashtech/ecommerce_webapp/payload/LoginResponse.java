package com.nashtech.ecommerce_webapp.payload;

import com.nashtech.ecommerce_webapp.models.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {
    private String email;
    private String fullName;
    private String token;



    public LoginResponse(String token) {
        this.token = token;
    }

    public LoginResponse(Account account, String token) {
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.token = token;
    }

    public LoginResponse(String email, String fullName, String token) {
        this.email = email;
        this.fullName = fullName;
        this.token = token;
    }
}
