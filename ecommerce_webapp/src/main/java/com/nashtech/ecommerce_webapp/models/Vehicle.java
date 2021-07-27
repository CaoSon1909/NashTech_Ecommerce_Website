package com.nashtech.ecommerce_webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "imageURL")
    private String imageURL;
    @Column(name = "description")
    private String description;
    @Column(name = "status", nullable = false)
    private boolean status;
    @Column(name = "categoryID", insertable = false, updatable = false)
    private UUID categoryID;
    @Column(name = "brandID", insertable = false, updatable = false)
    private UUID brandID;

    //===========//
    @ManyToOne
    @JoinColumn(name = "categoryID")
    @JsonBackReference(value = "vehicle_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandID")
    @JsonBackReference(value = "vehicle_brand")
    private Brand brand;
    //===========//

    public Vehicle(String imageURL,UUID id, String name, String color, long dateOfManufacture, float price, int quantity, boolean status, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dateOfManufacture = dateOfManufacture;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.imageURL = imageURL;
        this.category = category;
        this.brand = brand;
    }

    public Vehicle(@JsonProperty("id") UUID id
            ,@JsonProperty("name") String name
            ,@JsonProperty("color") String color
            ,@JsonProperty("dateOfManufacture") long dateOfManufacture
            ,@JsonProperty("price") float price
            ,@JsonProperty("quantity") int quantity
            ,@JsonProperty("status") boolean status
            ,@JsonProperty("imageURL") String imageURL
            ,@JsonProperty("description") String description
            ,@JsonProperty("categoryID") UUID categoryID
            ,@JsonProperty("brandID") UUID brandID) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dateOfManufacture = dateOfManufacture;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.imageURL = imageURL;
        this.description = description;
        this.categoryID = categoryID;
        this.brandID = brandID;
    }
}
