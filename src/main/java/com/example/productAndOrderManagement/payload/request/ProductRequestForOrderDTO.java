package com.example.productAndOrderManagement.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestForOrderDTO {

  private Long id;
  private int quantity;
}
