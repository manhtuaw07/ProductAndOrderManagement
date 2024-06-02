package com.example.productAndOrderManagement.service;

import com.example.productAndOrderManagement.domain.payload.request.OrderRequestDTO;
import com.example.productAndOrderManagement.domain.payload.response.OrderResponseDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface OrderService {

  void upsertOrder(Long id, OrderRequestDTO orderRequest);

  OrderResponseDTO getOrderById(Long id);
  Page<OrderResponseDTO> getAllOrders(int page, int size, String sort);
  List<OrderResponseDTO> searchOrders(String customerName, Long orderId, int page, int size, String sort);

}
