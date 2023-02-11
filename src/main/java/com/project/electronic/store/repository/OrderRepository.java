package com.project.electronic.store.repository;

import com.project.electronic.store.entity.Order;
import com.project.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUser(User user);
}
