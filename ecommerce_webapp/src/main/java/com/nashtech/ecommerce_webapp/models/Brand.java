package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Brand")
@Data
@NoArgsConstructor
public class Brand {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = false)
    private UUID id;
    @Column(name = "email", nullable = false, length = 320)
    private String email;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name =  "address", nullable = false)
    private String address;
    @Column(name = "website")
    private String website;
    @Column(name = "phoneNumber", nullable = false, length = 10)
    private String phoneNumber;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Vehicle> vehicles = new HashSet<>();

    public Brand(UUID id, String email, String name, String address, String website, String phoneNumber, Set<Vehicle> vehicles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.vehicles = vehicles;
    }
}
