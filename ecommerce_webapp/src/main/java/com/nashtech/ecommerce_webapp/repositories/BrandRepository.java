package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
