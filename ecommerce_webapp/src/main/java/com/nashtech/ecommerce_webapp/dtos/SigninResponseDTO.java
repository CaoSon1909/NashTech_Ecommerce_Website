package com.nashtech.ecommerce_webapp.dtos;

import com.nashtech.ecommerce_webapp.models.user.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponseDTO {
    private String jwt;
    private long id;
    private String username;
    private List<String> roles;
}
