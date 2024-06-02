package com.example.productAndOrderManagement.domain.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseForOrderDTO {

  private Long id;
  private String name;
  private String description;
  private double price;
}
