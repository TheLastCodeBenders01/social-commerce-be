package com.thelastcodebenders.social_commerce_be.repositories;

import com.thelastcodebenders.social_commerce_be.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserId(UUID userId);

    @Query("""
        SELECT DISTINCT o
        FROM Order o
        JOIN o.productIds pid
        WHERE pid IN :productIds
    """)
    List<Order> findOrdersByProductIdsContainingAny(@Param("productIds") List<Long> productIds);
}
