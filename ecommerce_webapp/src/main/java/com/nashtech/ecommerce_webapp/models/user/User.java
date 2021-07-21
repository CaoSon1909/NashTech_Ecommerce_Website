package com.nashtech.ecommerce_webapp.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Userr", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
            "email"
    }),
    @UniqueConstraint(columnNames = {
            "phoneNumber"
    }),
    @UniqueConstraint(columnNames = {
            "phoneNumber"
    }),
    @UniqueConstraint(columnNames = {
            "address"
    }),
    @UniqueConstraint(columnNames = {
            "fullname"
    }),
    @UniqueConstraint(columnNames = {
            "birthdate"
    }),
    @UniqueConstraint(columnNames = {
            "status"
    })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 320)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
    @NotBlank
    @Size(min=10, max = 11)
    private String phoneNumber;
    @NotBlank
    private String address;
    @NotBlank
    @Size(max = 50)
    @Column(name = "fullname")
    private String fullName;
    @NotBlank
    @Column(name = "birthdate")
    private long birthDate;
    @NotBlank
    private int status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
//    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

}
