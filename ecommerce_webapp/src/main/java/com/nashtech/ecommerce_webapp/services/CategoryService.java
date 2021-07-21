package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Category;
import com.nashtech.ecommerce_webapp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public List<Category> findAllCategories(){
        return this.categoryRepository.findAll();
    }

    @Transactional
    public Category findCategoryById(UUID id){
        return this.categoryRepository.findCategoryById(id);
    }

    @Transactional
    public List<Category> findCategoryByName(String name){
        return this.categoryRepository.findByNameContaining(name);
    }

    @Transactional
    public boolean createCategory(Category category){
        UUID id = UUID.randomUUID();
        String nativeQuery = "INSERT INTO Category (id, name, description) VALUES (?,?,?)";
        int row = em.createNativeQuery(nativeQuery)
                .setParameter(1, id)
                .setParameter(2, category.getName())
                .setParameter(3, category.getDescription())
                .executeUpdate();
        return row > 0;
    }

    @Transactional
    public boolean updateCategory(Category category){
        Category result = findCategoryById(category.getId());
        if (result != null){
            String nativeQuery = "UPDATE Category SET name = ?, description = ? " +
                    "WHERE id =?";
            int row = em.createNativeQuery(nativeQuery)
                    .setParameter(1, category.getName())
                    .setParameter(2, category.getDescription())
                    .setParameter(3, category.getId())
                    .executeUpdate();
            return row > 0;
        }
        return false;
    }

    @Transactional
    public boolean deleteCategory(UUID id){
        Category result = findCategoryById(id);
        if (result != null){
            String sql = "DELETE FROM Category WHERE id = ? ";
            int row = em.createNativeQuery(sql)
                    .setParameter(1, id).executeUpdate();
            return row > 0;
        }
        return false;
    }
}
