package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.adapter.PaymentServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.OrderNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.KorapayWebhookRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.OrderResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.PaymentResponse;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
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

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentServiceAdapter paymentServiceAdapter;
    private final ProductService productService;
    private final UserService userService;

    public void saveOder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public PaymentResponse initiateCheckout() {
        User user = UserUtil.getLoggedInUser();
        Cart cart = cartService.getLoggedInUserCart();

        Order order = Order.builder()
                .userId(user.getUserId())
                .totalAmount(productService.sumAmountByProductIds(cart.getProductIds()))
                .productIds(cart.getProductIds())
                .build();

        saveOder(order);
        return paymentServiceAdapter.initiatePayment(order, user);
    }

    @Transactional
    public AppResponse successfulPayment(KorapayWebhookRequest request) {

        log.info("Korapay Payload is: {}", request);

        UUID reference = UUID.fromString(request.getReference());

        log.info("Reference is {}", reference);
        Order order = getOrderById(reference);
        order.setPaid(true);

        if (request.getEvent().equals("charge.success")) {
            orderRepository.save(order);
            cartService.removeItemsInUserCart(order.getUserId());
        }

        String responseMessage = String.format("Payment status successfully updated to: %s", request.getEvent());

        log.info(responseMessage);

        return AppResponse.builder()
                .message(responseMessage)
                .status(HttpStatus.OK).build();
    }

    public List<OrderResponse> getUserOrders() {
        return convertOrdersToDto(orderRepository.findAllByUserId(UserUtil.getLoggedInUser().getUserId()));
    }

    public List<OrderResponse> getPurchasedOrdersForVendor() {
        List<Long> userProductIds = productService.getUserProducts().parallelStream().map(
                ProductResponse::getProductId
        ).toList();

        return convertOrdersToDto(orderRepository.findOrdersByProductIdsContainingAny(userProductIds));
    }

    public List<OrderResponse> convertOrdersToDto(List<Order> orders) {
        return orders.parallelStream().map(
                order -> {
                    User user = userService.findByUserId(order.getUserId());
                    return order.toDto(
                            order.getProductIds().parallelStream().map(
                                    productId -> productService.getProductById(productId).toDto()
                            ).toList(), user
                    );
                }
        ).toList();
    }
}
