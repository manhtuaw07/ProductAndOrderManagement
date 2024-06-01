package com.example.productAndOrderManagement.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

  private String name;
  private String description;
  private double price;
  private int quantity;
}
