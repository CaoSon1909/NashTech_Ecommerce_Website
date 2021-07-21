package com.nashtech.ecommerce_webapp.dtos;

import com.nashtech.ecommerce_webapp.models.user.RoleName;
import lombok.Data;

import java.util.List;

@Data
public class SigninRequestDTO {

    private String email;
    private String password;
    List<RoleName> roleNames;
}
