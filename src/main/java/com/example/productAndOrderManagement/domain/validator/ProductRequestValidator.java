package com.example.productAndOrderManagement.domain.validator;

import com.example.productAndOrderManagement.domain.model.Product;
import com.example.productAndOrderManagement.domain.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

    //validate price
    if (productRequestDTO.getPrice() < 0) {
      errors.rejectValue("price", "price.invalid", "Price must be greater than 0");
    }

  }
}
