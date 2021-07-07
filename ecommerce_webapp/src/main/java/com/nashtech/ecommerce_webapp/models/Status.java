package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Status")
public class Status implements Serializable {
    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

    //Account
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts = new HashSet<>();


    public Status() {
    }

    public Status(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
