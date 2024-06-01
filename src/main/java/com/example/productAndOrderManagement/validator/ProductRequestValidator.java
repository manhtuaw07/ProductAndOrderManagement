package com.example.productAndOrderManagement.validator;

import com.example.productAndOrderManagement.model.Product;
import com.example.productAndOrderManagement.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class ProductRequestValidator implements Validator {

  private final ProductService productService;

  @Override
  public boolean supports(Class<?> clazz) {
    return ProductRequestDTO.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Product name is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "field.required", "Product price is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "field.required", "Product quantity is required");

    ProductRequestDTO productRequestDTO = (ProductRequestDTO) target;

    //validate product name does not allow duplicate
    List<Product> allProducts = productService.getAllProducts();
    for (Product product : allProducts) {
      if (product.getName().equals(productRequestDTO.getName())) {
        errors.rejectValue("name", "name.duplicate", "Product name already exists");
        break;
      }
    }

    //validate price
    if (productRequestDTO.getPrice() < 0) {
      errors.rejectValue("price", "price.invalid", "Price must be greater than 0");
    }

    //validate quantity
    if (productRequestDTO.getQuantity() < 0) {
      errors.rejectValue("quantity", "quantity.invalid", "Quantity must be greater than 0");
    }

    //validate name length
    if (productRequestDTO.getName().length() < 3 || productRequestDTO.getName().length() > 50) {
      errors.rejectValue("name", "name.invalid", "Product name must be between 3 and 50 characters");
    }

  }
}
