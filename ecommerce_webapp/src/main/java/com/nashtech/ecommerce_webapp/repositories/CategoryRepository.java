package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Transactional
    List<Category> findAll();

    @Transactional
    Category findCategoryById(UUID uuid);

    @Transactional
    List<Category> findByNameContaining(String name);


}
