package com.example.productAndOrderManagement.domain.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestForOrderDTO {

  private Long id;
  private int quantity;
}
