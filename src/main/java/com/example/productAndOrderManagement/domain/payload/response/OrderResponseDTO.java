package com.example.productAndOrderManagement.domain.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
  private Long id;
  private String customerName;
  private String address;
  private String email;
  private String phoneNumber;
  private String status;
  private double paymentAmount;
  private String orderDate;
  private List<OrderDetailResponseDTO> orderDetails;

}
