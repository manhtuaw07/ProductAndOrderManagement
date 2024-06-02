package com.example.productAndOrderManagement.service;

import com.example.productAndOrderManagement.domain.model.Product;
import com.example.productAndOrderManagement.domain.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.domain.payload.response.ProductResponseDTO;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ProductService {

  ProductResponseDTO getProductById(Long id);

  void addProduct(ProductRequestDTO productRequest);

  void updateProduct(Long id, Map<String, Object> updates);

  Page<ProductResponseDTO> getAllProducts(int page, int size, String sort);

  List<Product> getAllProducts();

  Page<ProductResponseDTO> searchProducts(String keyword, int page, int size, String sort);

  void deleteProduct(Long id);

  void addAllProducts(List<ProductRequestDTO> productRequest);
}
