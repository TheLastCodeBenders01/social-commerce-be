package com.thelastcodebenders.social_commerce_be.repositories.specifications;

import com.thelastcodebenders.social_commerce_be.models.entities.Post;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> search(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            Predicate namePredicate = cb.like(cb.lower(root.get("contentUrl")), pattern);
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("caption")), pattern);

            return cb.or(namePredicate, descriptionPredicate);
        };
    }
}
