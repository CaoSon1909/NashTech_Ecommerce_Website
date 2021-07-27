package com.nashtech.ecommerce_webapp.dtos;

import com.nashtech.ecommerce_webapp.models.user.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    @NotBlank
    @Size(max = 320)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank
    private String retypePassword;
    @NotBlank
    private String fullName;
    private long birthdate;
    private String phoneNum;
    private String address;

    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }
}
