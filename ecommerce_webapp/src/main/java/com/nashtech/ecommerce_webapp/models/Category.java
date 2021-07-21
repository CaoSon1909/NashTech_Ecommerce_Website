package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Vehicle> vehicles = new HashSet<>();

    public Category(UUID id, String name, String description, Set<Vehicle> vehicles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vehicles = vehicles;
    }
}
