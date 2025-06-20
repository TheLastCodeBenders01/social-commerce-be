package com.thelastcodebenders.social_commerce_be.repositories;

import com.thelastcodebenders.social_commerce_be.models.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findAllByRoomIdOrderByCreatedAtAsc(UUID roomId, Pageable page);
}
