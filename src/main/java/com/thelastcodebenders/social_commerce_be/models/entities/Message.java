package com.thelastcodebenders.social_commerce_be.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Builder.Default private UUID messageId = UUID.randomUUID();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private UUID roomId;

    @Column(nullable = false, updatable = false)
    @Builder.Default private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    public enum MessageType {
        TEXT, IMAGE, VIDEO, FILE, AUDIO,
    }
}
