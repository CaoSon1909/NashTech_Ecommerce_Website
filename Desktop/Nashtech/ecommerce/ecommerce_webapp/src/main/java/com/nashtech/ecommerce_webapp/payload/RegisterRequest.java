package com.nashtech.ecommerce_webapp.payload;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String fullName;
    @NotNull
    private long birthDate;
    @NotNull
    private int roleID;
    @NotNull
    private int status;
}
