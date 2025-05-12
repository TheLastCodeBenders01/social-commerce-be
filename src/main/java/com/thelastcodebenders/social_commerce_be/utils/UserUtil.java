package com.thelastcodebenders.social_commerce_be.utils;

import com.thelastcodebenders.social_commerce_be.exceptions.SocialCommerceException;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static User getLoggedInUser() throws SocialCommerceException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();
            return ((User) principal);
        }
        else {
            throw new SocialCommerceException("Error getting logged in user");
        }
    }
}
