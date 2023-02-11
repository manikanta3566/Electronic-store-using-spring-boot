package com.project.electronic.store.repository;

import com.project.electronic.store.entity.Cart;
import com.project.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);

}
