package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Vehicle;
import com.nashtech.ecommerce_webapp.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class VehicleService {
    private final VehicleRepository repository;

    @Autowired
    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @PersistenceContext
    private EntityManager em;

    //Create a vehicle
    public boolean createVehicle(Vehicle vehicle){
        UUID id = UUID.randomUUID();
        String nativeQuery = "INSERT INTO Vehicle " +
                "(id, name, color, dateOfManufacture, price, quantity, status, categoryID, brandID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        int impactedRow =  em.createNativeQuery(nativeQuery)
                .setParameter(1, id )
                .setParameter(2,  vehicle.getName())
                .setParameter(3, vehicle.getColor())
                .setParameter(4, vehicle.getDateOfManufacture())
                .setParameter(5, vehicle.getPrice())
                .setParameter(6, vehicle.getQuantity())
                .setParameter(7, vehicle.getStatus())
                .setParameter(8, vehicle.getCategory())
                .setParameter(8, vehicle.getBrandID())
                .executeUpdate();
        return impactedRow > 0;
    }

    //Get vehicle by ID
    @Transactional
    public Optional<Vehicle> getVehicleByID(UUID uuid){
        return this.repository.findById(uuid);
    }


    private Page<Vehicle> searchVehiclesByName(String name, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByNameContainingAndStatusEquals(name, status, paging);
    }

    private Page<Vehicle> searchVehiclesByNameAndBrand(String name, UUID brandID, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByNameContainingAndBrandIDEqualsAndStatusEquals(name, brandID, status, paging);
    }

    private Page<Vehicle> searchVehiclesByNameAndCategory(String name, UUID categoryID, int status, int page, int size ){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByNameContainingAndCategoryIDEqualsAndStatusEquals(name, categoryID, status, paging);
    }

    private Page<Vehicle> searchByNameAndCategoryAndBrand(String name, UUID categoryID, UUID brandID, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByNameContainingAndCategoryIDEqualsAndBrandIDEqualsAndStatusEquals(name, categoryID, brandID, status, paging);
    }

    private Page<Vehicle> searchByBrand(UUID brandID, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByBrandIDEqualsAndStatusEquals(brandID, status, paging);
    }

    private Page<Vehicle> searchByBrandAndCategory(UUID brandID, UUID categoryID, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByBrandIDEqualsAndCategoryIDEqualsAndStatusEquals(brandID, categoryID, status, paging);
    }

    private Page<Vehicle> searchByCategory(UUID categoryID, int status, int page, int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));
        return this.repository.findByCategoryIDEqualsAndStatusEquals(categoryID, status, paging);
    }

    //Get vehicles like name and status
    @Transactional
    public Page<Vehicle> searchVehiclesWithFilter
    (int page, int size,  String name, UUID categoryID, UUID brandID, int status, String searchType){
        Pageable paging = PageRequest.of(page, size, Sort.by("dateOfManufacture"));

        Page<Vehicle> pageVehicles = null;

        if (name == null){
            pageVehicles = this.repository.findAll(paging);
        } else{

            switch (searchType){
                case "searchAll":
                    pageVehicles = this.repository.findByStatusEquals(status, paging); //search all with status
                    break;
                case "byName" :
                    pageVehicles = searchVehiclesByName(name, status, page, size);
                    break;
                case "byNameAndBrand" :
                    pageVehicles = searchVehiclesByNameAndBrand(name, brandID, status, page, size);
                    break;
                case "byNameAndCategory":
                    pageVehicles = searchVehiclesByNameAndCategory(name, categoryID, status, page, size);
                    break;
                case "byNameAndCategoryAndBrand":
                    pageVehicles = searchByNameAndCategoryAndBrand(name,categoryID, brandID, status, page, size);
                    break;
                case "byBrand":
                    pageVehicles = searchByBrand(brandID, status, page, size);
                    break;
                case "byBrandAndCategory":
                    pageVehicles = searchByBrandAndCategory(brandID, categoryID, status, page, size);
                    break;
                case "byCategory":
                    pageVehicles = searchByCategory(categoryID, status, page, size);
                    break;

            } //end switch
        }//end else
        return pageVehicles;
    }

    @Transactional
    public boolean updateVehicle(Vehicle vehicle){
        Optional<Vehicle> result = getVehicleByID(vehicle.getId());
        if (result.isPresent()){
            String nativeQuery =
            "UPDATE Vehicle SET name = ? , color = ?, dateOfManufacture =?, price=?, quantity=?, status=?, categoryID=?, brandID=? " +
                    "WHERE id = ?";
            int impactedRow = em.createNativeQuery(nativeQuery)
                    .setParameter(1, vehicle.getName())
                    .setParameter(2, vehicle.getColor())
                    .setParameter(3, vehicle.getDateOfManufacture())
                    .setParameter(4, vehicle.getPrice())
                    .setParameter(5, vehicle.getQuantity())
                    .setParameter(6, vehicle.getStatus())
                    .setParameter(7, vehicle.getCategoryID())
                    .setParameter(6, vehicle.getBrandID())
                    .executeUpdate();
            return impactedRow > 0;
        }
        return false;
    }

    @Transactional
    public boolean deleteVehicle(UUID id){
        Optional<Vehicle> result = getVehicleByID(id);
        if (result.isPresent()){
            this.repository.delete(result.get());
            return true;
        }
        return false;
    }



}
