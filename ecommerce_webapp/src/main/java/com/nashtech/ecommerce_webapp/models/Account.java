package com.nashtech.ecommerce_webapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Account")
public class Account implements Serializable {

    @Id
    @Column(name = "email", updatable = false, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name = "phoneNumber", length = 10, nullable = false)
    private String phoneNumber;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "fullname", length = 50, nullable = false)
    private String fullName;
    @Column(name = "birthdate", nullable = false)
    private long birthDate;



}
