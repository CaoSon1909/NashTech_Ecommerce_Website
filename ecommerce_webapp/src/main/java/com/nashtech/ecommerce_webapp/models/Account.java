package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Account")
public class Account implements Serializable {

    @Id
    @Column(name = "email", updatable = false, unique = false, nullable = false)
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
    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID")
    @JsonBackReference
    private Role role;

    @Column(name = "roleID", insertable = false, updatable = false)
    private int roleID;

    public Account() {
    }

    public Account(String email, String password, String phoneNumber, String address, String fullName, long birthDate, Role role, int status) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.role = role;
        this.status = status;
    }

    public Account(@JsonProperty("email") String email
            ,@JsonProperty("password") String password
            ,@JsonProperty("phoneNumber") String phoneNumber
            ,@JsonProperty("address") String address
            ,@JsonProperty("fullName") String fullName
            ,@JsonProperty("birthDate") long birthDate
            ,@JsonProperty("roleID") int roleID
            ,@JsonProperty("status") int status) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.roleID = roleID;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
