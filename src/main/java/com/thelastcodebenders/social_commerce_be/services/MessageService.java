package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.models.dto.ChatMessage;
import com.thelastcodebenders.social_commerce_be.models.dto.MessageRequest;
import com.thelastcodebenders.social_commerce_be.models.entities.Message;
import com.thelastcodebenders.social_commerce_be.models.entities.Room;
import com.thelastcodebenders.social_commerce_be.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;


    public Message sendMessage(UUID roomId, MessageRequest request) {
        Room room = roomService.getRoomById(roomId);

        Message message = Message.builder()
                .content(request.getContent())
                .roomId(roomId)
                .type(request.getMessageType())
                .build();

        room.getMessages().add(message);

        // first socket event
        emitMessageToSocket(
                ChatMessage.builder()
                        .sender(room.getFirstUser().toString())
                        .conversationId(roomId.toString())
                        .content(message)
                        .type(ChatMessage.MessageType.CHAT)
                        .build()
        );
        roomService.saveRoom(room);

        return message;
    }

    public void sendMessageToClients(Object payload, String destination) {
        // 'payload' is the data you want to send (e.g., a ChatMessage object)
        // 'destination' is the STOMP topic or queue that clients are subscribed to
        // Spring will automatically convert 'payload' to JSON (or other format)

        messagingTemplate.convertAndSend(destination, payload);

        log.info("Server sent message to WebSocket destination: {} with payload: {}", destination, payload);
    }

    public void emitMessageToSocket(ChatMessage message) {

        // Determine the destination based on your application logic
        // For a public chat:
//        String publicChatDestination = "/topic/public";
        // For a specific conversation:
         String conversationDestination = "/topic/conversation/" + message.getConversationId();

        sendMessageToClients(message, conversationDestination); // Or conversationDestination
//        return savedMessage;
    }

    public List<Message> getRoomMessages(UUID roomId, int pageNumber, int pageSize) {
        return messageRepository.findAllByRoomIdOrderByCreatedAtAsc(
                roomId, PageRequest.of(pageNumber, pageSize)).getContent();
    }
}
