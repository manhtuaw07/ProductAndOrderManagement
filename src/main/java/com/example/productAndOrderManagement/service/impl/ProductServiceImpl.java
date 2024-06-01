package com.example.productAndOrderManagement.service.impl;
import com.example.productAndOrderManagement.exception.ResourceNotFoundException;
import com.example.productAndOrderManagement.model.Product;
import com.example.productAndOrderManagement.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.payload.response.OrderResponseDTO;
import com.example.productAndOrderManagement.payload.response.ProductResponseDTO;
import com.example.productAndOrderManagement.repository.ProductRepository;
import com.example.productAndOrderManagement.repository.specification.ProductSpecification;
import com.example.productAndOrderManagement.service.ProductService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

  private final ModelMapper modelMapper;

  private final ProductRepository productRepository;

  public void addProduct(ProductRequestDTO productRequest) {
    logger.info("Adding new product: {}", productRequest.getName());
    Product product = modelMapper.map(productRequest, Product.class);
    Product savedProduct = productRepository.save(product);
    logger.info("Product added successfully: {}", savedProduct.getId());
  }

  public ProductResponseDTO getProductById(Long id) {
    logger.info("Fetching product by ID: {}", id);
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    return modelMapper.map(product, ProductResponseDTO.class);
  }

  public void updateProduct(Long id, Map<String, Object> updates) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    updates.forEach((update, value) -> {
      switch (update) {
        case "description":
          product.setDescription((String) value);
          break;
        case "price":
          product.setPrice(value instanceof Integer ? (Integer) value : (Double) value);
          break;
        default:
          throw new IllegalArgumentException("Invalid update field: " + update);
      }
    });

    productRepository.save(product);
  }

  public Page<ProductResponseDTO> getAllProducts(int page, int size, String sort) {
    logger.info("Fetching all products with pagination: page={}, size={}", page, size);

    //validate sort field
    if (!List.of("id", "name", "description", "price", "quantity").contains(sort)) {
      throw new IllegalArgumentException("Invalid sort field: " + sort);
    }

    Sort sortBy = Sort.by(Sort.Direction.ASC, sort);
    Pageable pageable = PageRequest.of(page, size, sortBy);
    Page<Product> productsPage = productRepository.findAll(pageable);
    return productsPage.map(product -> modelMapper.map(product, ProductResponseDTO.class));
  }

  @Override
  public List<Product> getAllProducts() {
    logger.info("Fetching all products ");
    return productRepository.findAll();
  }

  public Page<ProductResponseDTO> searchProducts(String keyword, int page, int size, String sort) {
    logger.info("Searching products with keyword: {}", keyword);
    Sort sortBy = Sort.by(Sort.Direction.ASC, sort);
    Pageable pageable = PageRequest.of(page, size, sortBy);

    Specification<Product> specification = Specification.where(ProductSpecification.nameContains(keyword)
        .or(ProductSpecification.descriptionContains(keyword)));
    Page<Product> productsPage = productRepository.findAll(specification, pageable);

    return productsPage.map(product -> modelMapper.map(product, ProductResponseDTO.class));
  }

  public void deleteProduct(Long id) {
    productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    logger.info("Deleting product with ID: {}", id);
    productRepository.deleteById(id);
  }
}
