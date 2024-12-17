package com.microservice.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(request);
    }

    public List<ProductPurchaseResponhse> purchaseProducts(List<ProductPurchaseRequest> request) {
    }

    public ProductResponse findById(Integer id) {
    }

    public List<ProductResponse> findAll() {
    }
}
