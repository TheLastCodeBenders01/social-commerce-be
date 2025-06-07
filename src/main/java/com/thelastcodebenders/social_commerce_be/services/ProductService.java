package com.thelastcodebenders.social_commerce_be.services;

import com.thelastcodebenders.social_commerce_be.adapter.FileServiceAdapter;
import com.thelastcodebenders.social_commerce_be.exceptions.ProductNotFoundException;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductRequest;
import com.thelastcodebenders.social_commerce_be.models.dto.ProductResponse;
import com.thelastcodebenders.social_commerce_be.models.entities.Product;
import com.thelastcodebenders.social_commerce_be.repositories.ProductRepository;
import com.thelastcodebenders.social_commerce_be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FileServiceAdapter fileServiceAdapter;

    public Product getProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

//    public List<Long> saveProducts(List<ProductRequest> productRequests) {
//        UUID userId = UserUtil.getLoggedInUser().getUserId();
//        return productRepository.saveAll(productRequests.parallelStream().map(productRequest -> productRequest.toDb(userId)).toList())
//                .parallelStream().map(Product::getProductId).toList();
//    }

//    public List<ProductResponse> saveProductsAndGetResponse(List<ProductRequest> productRequests) {
//        UUID userId = UserUtil.getLoggedInUser().getUserId();
//        return productRepository.saveAll(productRequests.parallelStream().map(productRequest -> productRequest.toDb(userId)).toList())
//                .parallelStream().map(Product::toDto).toList();
//    }

    public ProductResponse saveProduct(ProductRequest productRequest) {
        String productImageUrl = fileServiceAdapter.buildPinataFIleUri(fileServiceAdapter.uploadFileToPinata(productRequest.getImage()));

        UUID userId = UserUtil.getLoggedInUser().getUserId();
        return productRepository.save(productRequest.toDb(userId, productImageUrl)).toDto();
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
