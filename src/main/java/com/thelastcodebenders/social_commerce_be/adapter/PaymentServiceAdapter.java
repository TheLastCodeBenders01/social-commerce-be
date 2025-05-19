package com.thelastcodebenders.social_commerce_be.adapter;

import com.thelastcodebenders.social_commerce_be.models.dto.CustomerDetails;
import com.thelastcodebenders.social_commerce_be.models.dto.InitiateCheckoutRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.PaymentResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.types.Currency;
import com.thelastcodebenders.social_commerce_be.models.entities.Order;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceAdapter {
    @Value("${x-aggregator.secret-key}")
    private String xAggregatorSecretKey;

    @Value("${x-aggregator.notification-url}")
    private String xAggregatorNotificationUrl;

    @Value("${x-aggregator.redirect-url}")
    private String xAggregatorRedirectUrl;

    private final RestTemplate restTemplate;

    private final String xAggregatorBaseUrl = "https://x-aggregator-staging.up.railway.app";

    public PaymentResponse initiatePayment(Order order, User user) {
        InitiateCheckoutRequest checkoutRequest = InitiateCheckoutRequest.builder()
                .customer(
                        CustomerDetails.builder()
                                .name(String.format("%s %s", user.getFirstName(), user.getLastName()))
                                .email(user.getEmail())
                                .build()
                )
                .amount(order.getTotalAmount())
                .currency(Currency.NGN)
                .reference(order.getOrderId())
                .processor("kora")
                .redirectUrl(xAggregatorRedirectUrl)
                .notificationUrl(xAggregatorNotificationUrl)
                .narration("Order for social commerce application with orderId " + order.getOrderId())
                .mode("card")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + xAggregatorSecretKey);

        HttpEntity<InitiateCheckoutRequest> requestEntity = new HttpEntity<>(checkoutRequest, headers);

        log.info("Attempting to call x aggregator api with request: {}", requestEntity);
        return restTemplate.exchange(String.format("%s/api/v1/initiate", xAggregatorBaseUrl), HttpMethod.POST, requestEntity, PaymentResponse.class).getBody();
    }
}
