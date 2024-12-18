package com.microservice.ecommerce.product;

import com.microservice.ecommerce.exceptions.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Product not found");
        }
        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0;i<storedProducts.size();i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient quantity for product with ID::" + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchasesResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer id) {
        return productRepository.findById(id).map(productMapper::toProductResponse).orElseThrow(() -> new EntityNotFoundException("Product not found with id::" + id ));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }
}
