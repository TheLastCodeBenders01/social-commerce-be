package com.thelastcodebenders.social_commerce_be.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelastcodebenders.social_commerce_be.adapter.PaymentServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.OrderNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.KorapayWebhookRequest;
import com.thelastcodebenders.social_commerce_be.models.entities.Cart;
import com.thelastcodebenders.social_commerce_be.models.entities.Order;
import com.thelastcodebenders.social_commerce_be.models.entities.User;
import com.thelastcodebenders.social_commerce_be.repositories.OrderRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentServiceAdapter paymentServiceAdapter;
    private final ProductService productService;

    public void saveOder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public AppResponse initiateCheckout() {
        User user = UserUtil.getLoggedInUser();
        Cart cart = cartService.getLoggedInUserCart();

        Order order = Order.builder()
                .userid(user.getUserId())
                .totalAmount(productService.sumAmountByProductIds(cart.getProductIds()))
                .productIds(cart.getProductIds())
                .build();

        saveOder(order);
        return paymentServiceAdapter.initiatePayment(order, user);
    }

    @Transactional
    public AppResponse successfulPayment(Map<String, Object> plainRequest) {

        log.info("Korapay Payload is: {}", plainRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        KorapayWebhookRequest request = objectMapper.convertValue(plainRequest, KorapayWebhookRequest.class);
        UUID reference = UUID.fromString(request.getReference());

        log.info("Reference is {}", reference);
        Order order = getOrderById(reference);
        order.setPaid(true);

        if (request.getEvent().equals("charge.success")) {
            orderRepository.save(order);
            cartService.removeItemsInUserCart(order.getUserid());
        }

        String responseMessage = String.format("Payment status successfully updated to: %s", request.getEvent());

        log.info(responseMessage);

        return AppResponse.builder()
                .message(responseMessage)
                .status(HttpStatus.OK).build();
    }
}
