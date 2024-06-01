package com.example.productAndOrderManagement.controller;

import com.example.productAndOrderManagement.exception.ResourceNotFoundException;
import com.example.productAndOrderManagement.mapper.ErrorMapper;
import com.example.productAndOrderManagement.payload.request.OrderRequestDTO;
import com.example.productAndOrderManagement.payload.response.OrderResponseDTO;
import com.example.productAndOrderManagement.payload.response.ProductResponseDTO;
import com.example.productAndOrderManagement.service.OrderService;
import com.example.productAndOrderManagement.validator.OrderRequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  private final OrderRequestValidator orderRequestValidator;

  //UOD01: As an administrator, I would like to be able to create new orders with personal information and product listings to assist customers with placing orders.
  @Operation(summary = "Create a new order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order created successfully"),
      @ApiResponse(responseCode = "400", description = "Validation failed"),
      @ApiResponse(responseCode = "500", description = "Failed to create order", content = @Content)
  })
  @PostMapping("/create")
  public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequestDTO orderRequest, BindingResult bindingResult) {
    orderRequestValidator.validate(orderRequest, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMapper.errorsToMap(bindingResult.getAllErrors()));
    }
    orderService.upsertOrder(null, orderRequest);
    return ResponseEntity.ok("Order created successfully");
  }

  //UOD02: As an administrator, I would like to be able to view detailed information of an order based on ID to check order status.
  @Operation(summary = "Get order by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Order not found"),
      @ApiResponse(responseCode = "500", description = "Failed to fetch order", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> getOrderById(@PathVariable Long id) {
    OrderResponseDTO orderResponse = orderService.getOrderById(id);
    return ResponseEntity.ok(orderResponse);
  }

  //UOD03: As an administrator, I would like to be able to update an order's information to serve operational requirements and customer requests.
  @Operation(summary = "Update order by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order updated successfully"),
      @ApiResponse(responseCode = "404", description = "Order not found"),
      @ApiResponse(responseCode = "400", description = "Validation failed"),
      @ApiResponse(responseCode = "500", description = "Failed to update order", content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO orderRequest, BindingResult bindingResult) {
    orderRequestValidator.validate(orderRequest, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMapper.errorsToMap(bindingResult.getAllErrors()));
    }

    orderService.upsertOrder(id, orderRequest);
    return ResponseEntity.ok("Order updated successfully");
  }

  //UOD04: As an administrator, I would like to be able to get a list of all orders to manage the status of orders.
  @Operation(summary = "Get all orders")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
      @ApiResponse(responseCode = "500", description = "Failed to fetch orders", content = @Content)
  })
  @GetMapping("/all")
  public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
    Page<OrderResponseDTO> allOrders = orderService.getAllOrders(page, size, sort);
    return ResponseEntity.ok(allOrders);
  }

  //UOD05: As an administrator, I would like to be able to search for orders based on customer name or order ID to easily search for specific orders.
  @Operation(summary = "Search orders")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
      @ApiResponse(responseCode = "500", description = "Failed to fetch orders", content = @Content)
  })
  @GetMapping("/search")
  public ResponseEntity<?> searchOrders(@RequestParam(required = false) String customerName, @RequestParam(required = false) Long orderId,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort){
    List<OrderResponseDTO> orderResponses = orderService.searchOrders(customerName, orderId, page, size, sort);
    return ResponseEntity.ok(orderResponses);
  }
}
