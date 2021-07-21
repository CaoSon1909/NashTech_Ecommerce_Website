package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Vehicle")
@Data
@NoArgsConstructor
public class Vehicle implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "color", nullable = false, length = 20)
    private String color;
    @Column(name = "dateOfManufacture", nullable = false)
    private long dateOfManufacture;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "categoryID", insertable = false, updatable = false)
    private UUID categoryID;
    @Column(name = "brandID", insertable = false, updatable = false)
    private UUID brandID;

    //===========//
    @ManyToOne
    @JoinColumn(name = "categoryID")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandID")
    @JsonBackReference
    private Brand brand;
    //===========//

    public Vehicle(UUID id, String name, String color, long dateOfManufacture, float price, int quantity, int status, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dateOfManufacture = dateOfManufacture;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
        this.brand = brand;
    }

    public Vehicle(@JsonProperty("id") UUID id
            ,@JsonProperty("name") String name
            ,@JsonProperty("color") String color
            ,@JsonProperty("dateOfManufacture") long dateOfManufacture
            ,@JsonProperty("price") float price
            ,@JsonProperty("quantity") int quantity
            ,@JsonProperty("status") int status
            ,@JsonProperty("categoryID") UUID categoryID
            ,@JsonProperty("brandID") UUID brandID) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dateOfManufacture = dateOfManufacture;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.categoryID = categoryID;
        this.brandID = brandID;
    }
}
