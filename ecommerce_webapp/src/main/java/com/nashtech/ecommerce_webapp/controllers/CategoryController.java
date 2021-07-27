package com.nashtech.ecommerce_webapp.controllers;

import com.nashtech.ecommerce_webapp.models.Category;
import com.nashtech.ecommerce_webapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/categories/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<String> createCategory(@RequestBody Category category){
        boolean result = this.categoryService.createCategory(category);
        if (result){
            return new ResponseEntity<>("Created success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Create fail", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/public/categories")
    ResponseEntity<List<Category>> findAllCategories(){
        List<Category> result = this.categoryService.findAllCategories();
        if (!result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categories/search")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    ResponseEntity<List<Category>> findCategoriesByName(@RequestParam String name){
        List<Category> result = this.categoryService.findCategoryByName(name);
        if (!result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/categories")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<String> updateCategory(@RequestParam Category category){
        boolean result = this.categoryService.updateCategory(category);
        if (result){
            return new ResponseEntity<>("Updated category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Update category fail", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/categories")
    ResponseEntity<String> deleteCategory(@RequestParam UUID id){
        boolean result = this.categoryService.deleteCategory(id);
        if (result){
            return new ResponseEntity<>("Delete category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete category fail", HttpStatus.NOT_FOUND);
    }

}
