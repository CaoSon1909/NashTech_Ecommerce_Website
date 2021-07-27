package com.nashtech.ecommerce_webapp.dtos;

import com.nashtech.ecommerce_webapp.models.user.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequestDTO {

    private String email;
    private String password;
}
