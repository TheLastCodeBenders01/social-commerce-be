package com.thelastcodebenders.social_commerce_be.models.dto;

import com.thelastcodebenders.social_commerce_be.models.entities.Message;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {
    private String content;
    private Message.MessageType messageType;
}
