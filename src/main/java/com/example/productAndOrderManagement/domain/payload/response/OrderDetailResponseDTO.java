package com.example.productAndOrderManagement.domain.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponseDTO {

  private ProductResponseForOrderDTO product;
  private int quantity;
}
