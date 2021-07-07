package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
