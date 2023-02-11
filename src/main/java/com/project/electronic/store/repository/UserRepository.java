package com.project.electronic.store.repository;

import com.project.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByUsernameContaining(String keyword);

    Optional<User> findByEmail(String email);
}
