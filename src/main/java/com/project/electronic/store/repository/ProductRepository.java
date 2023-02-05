package com.project.electronic.store.repository;

import com.project.electronic.store.entity.Category;
import com.project.electronic.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByNameContaining(String keyword);

    List<Product> findByCategory(Category category);
}
