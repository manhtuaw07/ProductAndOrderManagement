package com.example.productAndOrderManagement.service;

import com.example.productAndOrderManagement.model.Product;
import com.example.productAndOrderManagement.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.payload.response.ProductResponseDTO;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponseDTO getProductById(Long id);

  void addProduct(ProductRequestDTO productRequest);

  void updateProduct(Long id, Map<String, Object> updates);

  Page<ProductResponseDTO> getAllProducts(int page, int size, String sort);

  List<Product> getAllProducts();

  Page<ProductResponseDTO> searchProducts(String keyword, int page, int size, String sort);

  void deleteProduct(Long id);
}
