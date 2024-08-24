package com.magazin.commerce.repository;

import com.magazin.commerce.entity.User;
import com.magazin.commerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   Optional<com.magazin.commerce.entity.User> findFirstByEmail(String email);
   User findByRole(UserRole userRole);

}
