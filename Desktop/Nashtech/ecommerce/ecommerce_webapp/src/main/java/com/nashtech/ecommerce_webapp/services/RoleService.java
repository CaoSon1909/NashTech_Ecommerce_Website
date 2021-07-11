package com.nashtech.ecommerce_webapp.services;

import com.nashtech.ecommerce_webapp.models.Role;
import com.nashtech.ecommerce_webapp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getAllRole(){
        return repository.findAll();
    }
}
