package com.thelastcodebenders.social_commerce_be.repositories;

import com.thelastcodebenders.social_commerce_be.models.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findAllByFirstUserOrSecondUser(UUID firstUser, UUID secondUser);

    @Query("SELECT r FROM Room r WHERE (r.firstUser = :userA AND r.secondUser = :userB) OR (r.firstUser = :userB AND r.secondUser = :userA)")
    Optional<Room> findByUsers(UUID userA, UUID userB);
}
