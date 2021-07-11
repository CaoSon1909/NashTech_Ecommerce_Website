package com.nashtech.ecommerce_webapp.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    public String email;
    @NotNull
    public String password;

    public LoginRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}

