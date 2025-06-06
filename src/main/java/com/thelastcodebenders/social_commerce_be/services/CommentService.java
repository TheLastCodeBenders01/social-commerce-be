package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.models.entities.Comment;
import com.thelastcodebenders.social_commerce_be.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
