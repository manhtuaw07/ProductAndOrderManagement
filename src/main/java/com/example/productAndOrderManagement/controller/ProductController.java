package com.example.productAndOrderManagement.controller;

import com.example.productAndOrderManagement.exception.ResourceNotFoundException;

import com.example.productAndOrderManagement.mapper.ErrorMapper;
import com.example.productAndOrderManagement.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.payload.response.ProductResponseDTO;
import com.example.productAndOrderManagement.service.ProductService;
import com.example.productAndOrderManagement.validator.ProductRequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  private final ProductRequestValidator productRequestValidator;

  //USP01: As an admin, I want to be able to add a new product to the store to update the product list.
  @Operation(summary = "Add a new product")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Product added successfully"),
    @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
    @ApiResponse(responseCode = "500", description = "Failed to add product", content = @Content)
  })
  @PostMapping("/add")
  public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequestDTO productRequest, BindingResult bindingResult) {
    productRequestValidator.validate(productRequest, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMapper.errorsToMap(bindingResult.getAllErrors()));
    }
    productService.addProduct(productRequest);
    return ResponseEntity.ok("Product added successfully");
  }

  //USP02: As an administrator, I want to be able to view detailed information of a product based on the ID to check the product's attributes.
  @Operation(summary = "Get a product by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to fetch product", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable Long id) {
    ProductResponseDTO productResponse = productService.getProductById(id);
    return ResponseEntity.ok(productResponse);
  }

  //USP03: As an administrator, I want to be able to update a product's information to adjust the price or product description.
  @PatchMapping("/{id}")
  @Operation(summary = "Update a product by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to update product", content = @Content)
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A map where the key is the field name and the value is the new value. Only include the fields you want to update.",
      content = @Content(schema = @Schema(example = "{\"description\": \"New product description\", \"price\": 1000}")))
  public ResponseEntity<?> updateProduct(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Map<String, Object> updates) {
    productService.updateProduct(id, updates);
    return ResponseEntity.ok("Product updated successfully");
  }

  //USP04: As an administrator, I want to be able to delete a product based on ID to remove products that are no longer needed.
  @Operation(summary = "Delete a product by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to delete product", content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok("Product deleted successfully");
  }

  //USP05: As an admin, I would like to be able to get a list of all products to see the entire list of products available in the store.
  @Operation(summary = "Get all products")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
      @ApiResponse(responseCode = "404", description = "No products found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to fetch products", content = @Content)
  })
  @GetMapping("/all")
  public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
    Page<ProductResponseDTO> productResponses = productService.getAllProducts(page, size, sort);
    return ResponseEntity.ok(productResponses);
  }

  //USP06: As an administrator, I would like to be able to search for products based on name or description to easily find specific products.
  @Operation(summary = "Search for products based on name or description")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
      @ApiResponse(responseCode = "404", description = "No products found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to fetch products", content = @Content)
  })
  @GetMapping("/search")
  public ResponseEntity<?> searchProducts(@RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
    Page<ProductResponseDTO> productResponses = productService.searchProducts(keyword, page, size, sort);
    return ResponseEntity.ok(productResponses);
  }
}
