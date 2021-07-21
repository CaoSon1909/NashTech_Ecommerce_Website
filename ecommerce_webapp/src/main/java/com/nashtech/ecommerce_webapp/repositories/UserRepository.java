package com.nashtech.ecommerce_webapp.repositories;

import com.nashtech.ecommerce_webapp.models.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String username);

    @Transactional
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("SELECT a FROM User a WHERE a.fullName LIKE %:name%")
    List<User> getAccountLikeName(@Param("name") String name);

}
