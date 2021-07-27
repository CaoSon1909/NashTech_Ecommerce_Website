package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    //test
    Page<Vehicle> findAllByNameLike(String name, Pageable pageable);

    //find by name and status
    Page<Vehicle> findAllByNameLikeAndStatusEquals(String name, int status, Pageable pageable);

    //find by (name, brand, status)
    Page<Vehicle> findByNameContainingAndBrandIDEqualsAndStatusEquals(String name, UUID brandID, int status, Pageable pageable);

    //find by (name ,category, status)
    Page<Vehicle> findByNameContainingAndCategoryIDEqualsAndStatusEquals(String name, UUID categoryID, int status, Pageable pageable);

    //find by (name, category, brand, status)
    Page<Vehicle> findByNameContainingAndCategoryIDEqualsAndBrandIDEqualsAndStatusEquals(String name, UUID categoryID, UUID brandID, int status, Pageable pageable);

    //find by (brand, status)
    Page<Vehicle> findByBrandIDEqualsAndStatusEquals(UUID brandID, int status, Pageable pageable);

    //find by (brand, category, status)
    Page<Vehicle> findByBrandIDEqualsAndCategoryIDEqualsAndStatusEquals(UUID brandID, UUID categoryID, int status, Pageable pageable);

    //find by (category, status)
    Page<Vehicle> findByCategoryIDEqualsAndStatusEquals(UUID categoryID, int status, Pageable pageable);

    //find by status
    Page<Vehicle> findByStatusEquals(int status, Pageable pageable);

    //find vehicle by categoryID = ? and status = ?
    Page<Vehicle> findByStatusEqualsAndPriceBetween(int status, float price, float price2, Pageable pageable);



}
