package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role implements Serializable {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts = new HashSet<>();

}
