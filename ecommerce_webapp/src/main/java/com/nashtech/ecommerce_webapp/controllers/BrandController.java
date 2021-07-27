package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.models.Brand;
import com.nashtech.ecommerce_webapp.services.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BrandController {

    @Autowired
    BrandService brandService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping(value = "/public/brands")
    public ResponseEntity<List<Brand>> getAllBrands(){
        List<Brand> result = brandService.getAllBrands();
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
