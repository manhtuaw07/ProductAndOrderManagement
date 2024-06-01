package com.example.productAndOrderManagement.payload.response;

import com.example.productAndOrderManagement.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponseDTO {

  private ProductResponseForOrderDTO product;
  private int quantity;
}
