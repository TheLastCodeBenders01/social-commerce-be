package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.models.dto.IdResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.MessageRequest;
import com.thelastcodebenders.social_commerce_be.models.entities.Message;
import com.thelastcodebenders.social_commerce_be.models.entities.Room;
import com.thelastcodebenders.social_commerce_be.services.MessageService;
import com.thelastcodebenders.social_commerce_be.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("messages")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final RoomService roomService;

    @Operation(summary = "get or create room if not exist")
    @PostMapping("room")
    public Room getOrCreateRoom(@RequestParam(name = "secondUser") UUID secondUser) {
        return roomService.getOrCreateRoom(secondUser);
    }

    @Operation(summary = "send message to room")
    @PostMapping("send/{roomId}")
    public Message sendMessage(@PathVariable UUID roomId, @ModelAttribute MessageRequest request) {
        return messageService.sendMessage(roomId, request);
    }

    @Operation(summary = "get user rooms")
    @GetMapping("user")
    public List<Room> getUserRooms() {
        return roomService.getUserRooms();
    }

    @Operation(summary = "get room messages")
    @GetMapping("room")
    public List<Message> getRoomMessages(
            @RequestParam(name = "roomId") UUID roomId, @RequestParam(name = "pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize
    ) {
        return messageService.getRoomMessages(roomId, pageNumber, pageSize);
    }
}
