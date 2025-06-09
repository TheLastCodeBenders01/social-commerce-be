package com.thelastcodebenders.social_commerce_be.repositories;

import com.thelastcodebenders.social_commerce_be.models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findAllByUserIdOrderByCreatedAtAsc(UUID userId, Pageable page);
    @Query("SELECT e FROM Post e ORDER BY e.createdAt DESC")
    Page<Post> findAllOrderByCreatedAtDesc(Pageable pageable);
}
