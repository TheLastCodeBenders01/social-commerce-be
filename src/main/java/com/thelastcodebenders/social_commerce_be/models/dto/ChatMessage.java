package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Generates getters, setters, toString, equals, and hashCode
@Builder
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all fields
public class ChatMessage {
    private String sender;    // Who sent the message
    private Object content;   // The message text
    private MessageType type; // Type of message (e.g., CHAT, JOIN, LEAVE)
    private String conversationId;

    // Enum for different message types
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
