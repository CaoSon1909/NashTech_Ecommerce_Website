package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role")
@Data
public class Role implements Serializable {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 50, nullable = false)
    @NaturalId
    private RoleName name;
    @Column(name = "description")
    private String description;

    //Account
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts = new HashSet<>();

    public Role() {
        super();
    }

    public Role(int id, RoleName name, String description, Set<Account> accounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accounts = accounts;
    }
}
