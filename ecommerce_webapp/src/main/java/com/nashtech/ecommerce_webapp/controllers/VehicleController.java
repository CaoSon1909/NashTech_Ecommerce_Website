package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.commons.Constant;
import com.nashtech.ecommerce_webapp.models.Vehicle;
import com.nashtech.ecommerce_webapp.services.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class VehicleController {

    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    //Get vehicles by name and status + paging
    @GetMapping(value = "/search/vehicles")
    public ResponseEntity<Map<String, Object>> getAllVehicles(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(defaultValue = Constant.ZERO_BASED_PAGE_INDEX, value = "page") int page,
            @RequestParam(defaultValue = Constant.SIZE_OF_PAGE_RETURNED, value = "size") int size,
            @RequestParam(required = false, value = "categoryID") UUID categoryID,
            @RequestParam(required = false, value = "brandID") UUID brandID,
            @RequestParam(value = "searchType", defaultValue = "searchAll") String searchType,
            @RequestParam(value = "status", defaultValue = "1") int status
    ){
        try {

            Page<Vehicle> pageVehicles = service.searchVehiclesWithFilter(page, size, name, categoryID, brandID, status, searchType);

            if (pageVehicles != null){
                List<Vehicle> vehicles = pageVehicles.getContent();
                int currentPage = pageVehicles.getNumber();
                long totalVehicles = pageVehicles.getTotalElements();
                int totalPages = pageVehicles.getTotalPages();

                Map<String, Object> response = new HashMap<>();
                response.put("VEHICLES", vehicles);
                response.put("CURRENT_PAGE", currentPage);
                response.put("TOTAL_VEHICLES", totalVehicles);
                response.put("TOTAL_PAGE", totalPages);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }//end if
        }//end try
        catch (Exception ex){
            log.error("Vehicle Controller - EXCEPTION: " + ex.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/vehicles/all")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> result = service.getAllVehicles();
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value= "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createVehicle(@RequestBody Vehicle vehicle){
        boolean result = service.createVehicle(vehicle);
        if (result){
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
//=====================================
    @GetMapping(value = "/vehicles/all/paging")
    public ResponseEntity<Map<String, Object>> searchLikeName(
            @RequestParam(defaultValue = "") String keyword
            , @RequestParam(defaultValue = Constant.ZERO_BASED_PAGE_INDEX, value = "page") int page
            , @RequestParam(defaultValue = Constant.SIZE_OF_PAGE_RETURNED, value = "size") int size){
        Page<Vehicle> result = service.searchVehiclesLikeName(keyword, page, size);
        if (result != null){
            List<Vehicle> listResult = result.getContent();
            int currentPage = result.getNumber();
            long totalVehicles = result.getTotalElements();
            int totalPages = result.getTotalPages();

            Map<String, Object> response = new HashMap<>();
            response.put("VEHICLES", listResult);
            response.put("CURRENT_PAGE", currentPage);
            response.put("TOTAL_VEHICLES", totalVehicles);
            response.put("TOTAL_PAGE", totalPages);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //view product details => permit all
    @GetMapping(value = "/vehicles/details")
    public ResponseEntity<Vehicle> viewDetailVehicle(@RequestParam(value = "id") UUID uuid){
        Optional<Vehicle> result = service.getVehicleByID(uuid);
        if (result.isPresent()){
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //update product
    @PutMapping("/vehicles")
    public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicle){
        boolean result = service.updateVehicle(vehicle);
        if (result){
            return new ResponseEntity<>("Update success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Update fail", HttpStatus.NOT_FOUND);
    }

    //delete product
    @DeleteMapping("/vehicles")
    public ResponseEntity<String> deleteVehicle(@RequestParam UUID id){
        boolean result = service.deleteVehicle(id);
        if (result){
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete fail", HttpStatus.NOT_FOUND);
    }
}
