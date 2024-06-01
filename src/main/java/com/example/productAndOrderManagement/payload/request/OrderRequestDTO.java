package com.example.productAndOrderManagement.payload.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
  private String customerName;
  private String address;
  private String email;
  private String phoneNumber;
  private String status;
  private List<ProductRequestForOrderDTO> products;
}
