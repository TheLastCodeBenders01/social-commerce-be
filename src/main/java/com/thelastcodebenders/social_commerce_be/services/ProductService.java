package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.exceptions.ProductNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import com.thelastcodebenders.social_commerce_be.repositories.ProductRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public List<Long> saveProducts(List<ProductRequest> productRequests) {
        return productRepository.saveAll(productRequests.parallelStream().map(ProductRequest::toDb).toList())
                .parallelStream().map(Product::getProductId).toList();
    }

    public List<ProductResponse> findAllById(List<Long> productIds) {
        return productRepository.findAllById(productIds).parallelStream().map(Product::toDto).toList();
    }

    public List<ProductResponse> getUserProducts() {
        return productRepository.findAllByUserId(UserUtil.getLoggedInUser().getUserId()).parallelStream().map(Product::toDto).toList();
    }

    public Double sumAmountByProductIds(List<Long> productIds) {
        double sum = 0;
        for (Long productId: productIds) {
            sum += getProductById(productId).getAmount();
        }
        return sum;
    }
}
