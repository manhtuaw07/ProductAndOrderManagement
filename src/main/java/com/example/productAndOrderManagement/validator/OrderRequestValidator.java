package com.example.productAndOrderManagement.validator;

import com.example.productAndOrderManagement.payload.request.OrderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class OrderRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return OrderRequestDTO.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "field.required", "Customer name is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field.required", "Address is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Email is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "field.required", "Phone number is required");

    OrderRequestDTO orderRequestDTO = (OrderRequestDTO) target;

    //validate email format
    if (!orderRequestDTO.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
      errors.rejectValue("email", "email.invalid", "Invalid email format");
    }

    //validate phone number format
    if (!orderRequestDTO.getPhoneNumber().matches("^\\+?(\\d{1,3})?[-.\\s]?(\\(?\\d{3}\\)?)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$")) {
      errors.rejectValue("phoneNumber", "phone.invalid", "Invalid phone number format");
    }

    //validate status
    if (orderRequestDTO.getStatus() != null && !orderRequestDTO.getStatus().matches("^(|PENDING|SHIPPING|SHIPPED|CANCELLED)$")) {
      errors.rejectValue("status", "status.invalid", "Invalid status");
    }
  }
}

