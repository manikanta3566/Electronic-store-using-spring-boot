package com.project.electronic.store.repository;

import com.project.electronic.store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,String> {

    @Query(value = "select id from cart_items where cart_id=:id",nativeQuery = true)
    List<String> findByCartId(@Param("id") String cartId);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_items where id IN(:ids)",nativeQuery = true)
    void deleteCartItems(@Param("ids") List<String> ids);
}
