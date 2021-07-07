package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
