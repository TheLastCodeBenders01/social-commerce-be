package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.exceptions.RoomNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.IdResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Room;
import com.thelastcodebenders.social_commerce_be.repositories.RoomRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getUserRooms() {
        UUID userId = UserUtil.getLoggedInUser().getUserId();
        return roomRepository.findAllByFirstUserOrSecondUser(userId, userId);
    }

    public IdResponse createRoom(UUID secondUser) {
        Room room = Room.builder()
                .firstUser(UserUtil.getLoggedInUser().getUserId())
                .secondUser(secondUser)
                .messages(new ArrayList<>())
                .build();
        saveRoom(room);

        return IdResponse.builder()
                .message("Room successfully created")
                .id(room.getRoomId().toString())
                .build();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(UUID roomId) {
        return roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }
}
