package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.user.Role;
import com.nashtech.ecommerce_webapp.models.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
