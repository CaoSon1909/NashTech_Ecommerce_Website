package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Brand;
import com.nashtech.ecommerce_webapp.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Brand> getAllBrands(){
        String sql = "SELECT * FROM Brand";
        return em.createNativeQuery(sql).getResultList();
    }
}
