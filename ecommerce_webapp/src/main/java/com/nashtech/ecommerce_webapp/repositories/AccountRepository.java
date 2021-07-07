package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("SELECT a FROM Account a WHERE a.fullName LIKE %:name%")
    List<Account> getAccountLikeName(@Param("name") String name);
}
