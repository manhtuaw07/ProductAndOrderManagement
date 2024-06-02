package com.example.productAndOrderManagement.service.impl;

import com.example.productAndOrderManagement.domain.exception.ResourceNotFoundException;
import com.example.productAndOrderManagement.domain.model.Order;
import com.example.productAndOrderManagement.domain.model.OrderDetail;
import com.example.productAndOrderManagement.domain.model.OrderStatus;
import com.example.productAndOrderManagement.domain.model.Product;
import com.example.productAndOrderManagement.domain.payload.request.OrderRequestDTO;
import com.example.productAndOrderManagement.domain.payload.request.ProductRequestForOrderDTO;
import com.example.productAndOrderManagement.domain.payload.response.OrderResponseDTO;
import com.example.productAndOrderManagement.repository.OrderDetailRepository;
import com.example.productAndOrderManagement.repository.OrderRepository;
import com.example.productAndOrderManagement.repository.ProductRepository;
import com.example.productAndOrderManagement.repository.specification.OrderSpecification;
import com.example.productAndOrderManagement.service.OrderService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
  private final ModelMapper modelMapper;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderDetailRepository orderDetailRepository;

  @Transactional
  @Override
  public void upsertOrder(Long id, OrderRequestDTO orderRequest) {
    Order order = new Order();
    if (id != null) {
      order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order id: " + id));
    }

    getOrder(order, orderRequest);
    Order savedOrder = orderRepository.save(order);
    double totalAmount = 0;

    List<Product> products = getProductsFromOrderRequest(orderRequest);

    // Create a map for quick lookup
    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));

    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(savedOrder.getId())
        .orElseGet(ArrayList::new);

    // For each product in the orderRequest, create an OrderDetail
    totalAmount = preProcessOrderInfo(orderRequest, productMap, savedOrder, orderDetails, totalAmount);
    saveOrderInfoInToDatabase(orderDetails, products, totalAmount, order);
  }

  private List<Product> getProductsFromOrderRequest(OrderRequestDTO orderRequest) {
    // Get all product IDs from the order request
    List<Long> productIds = orderRequest.getProducts().parallelStream()
        .map(ProductRequestForOrderDTO::getId)
        .collect(Collectors.toList());

    List<Product> products = productRepository.findAllById(productIds);

    // Check if all products were found
    if (products.size() != productIds.size()) {
      throw new ResourceNotFoundException("Products with the following IDs were not found: " + productIds);
    }
    return products;
  }

  private static double preProcessOrderInfo(OrderRequestDTO orderRequest, Map<Long, Product> productMap,
      Order savedOrder, List<OrderDetail> orderDetails, double totalAmount) {
    for (ProductRequestForOrderDTO productRequest : orderRequest.getProducts()) {
      Product product = productMap.get(productRequest.getId());

      //TODO: could be process in InventoryService in future
/*
      //validate if the product is in stock
      if (product.getQuantity() < productRequest.getQuantity()) {
        throw new ResourceNotFoundException("Product out of stock");
      }
      //update quantity of the product
      product.setQuantity(product.getQuantity() - productRequest.getQuantity());
*/

      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setOrder(savedOrder);
      orderDetail.setProduct(product);
      orderDetail.setQuantity(productRequest.getQuantity());

      orderDetails.add(orderDetail);

      totalAmount += product.getPrice() * productRequest.getQuantity();
    }
    return totalAmount;
  }

  private void saveOrderInfoInToDatabase(List<OrderDetail> orderDetails, List<Product> products, double totalAmount, Order order) {
    logger.info("Saving order details and updating product quantity in the database: {}", order);
    logger.info("Order details: {}", orderDetails);
    logger.info("Products: {}", products);

    orderDetailRepository.saveAll(orderDetails);
    productRepository.saveAll(products);
    order.setPaymentAmount(totalAmount);
    orderRepository.save(order);
  }

  private static void getOrder(Order order, OrderRequestDTO orderRequest) {
    order.setCustomerName(orderRequest.getCustomerName());
    order.setAddress(orderRequest.getAddress());
    order.setEmail(orderRequest.getEmail());
    order.setPhoneNumber(orderRequest.getPhoneNumber());
    order.setStatus(!orderRequest.getStatus().isEmpty() ? OrderStatus.valueOf(orderRequest.getStatus()) : OrderStatus.PENDING);
    order.setOrderDate(new Date());
  }

  public OrderResponseDTO getOrderById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order id: " + id));
    return modelMapper.map(order, OrderResponseDTO.class);
  }

  public Page<OrderResponseDTO> getAllOrders(int page, int size, String sort) {
    logger.info("Fetching all orders with pagination: page={}, size={}", page, size);

    Sort sortBy = Sort.by(Sort.Direction.ASC, sort);
    Pageable pageable = PageRequest.of(page, size, sortBy);
    Page<Order> ordersPage = orderRepository.findAll(pageable);
    return ordersPage.map(order -> modelMapper.map(order, OrderResponseDTO.class));
  }

  public List<OrderResponseDTO> searchOrders(String customerName, Long orderId, int page, int size, String sort) {
    logger.info("Searching orders with customerName: {}, orderId: {}", customerName, orderId);

    Sort sortBy = Sort.by(Sort.Direction.ASC, sort);
    Pageable pageable = PageRequest.of(page, size, sortBy);

    Page<Order> orders = null;
    if (customerName != null && orderId != null) {
      orders = orderRepository.findByCustomerNameContainingAndId(customerName, orderId, pageable);
    } else if (customerName != null) {
      Specification<Order> specification = Specification.where(OrderSpecification.customerNameContains(customerName));
      orders = orderRepository.findAll(specification, pageable);
    } else if (orderId != null) {
      Specification<Order> specification = Specification.where(OrderSpecification.orderIdContains(orderId));
      orders = orderRepository.findAll(specification, pageable);
    } else {
      orders = orderRepository.findAll(pageable);
    }
    return orders.stream().map(order -> modelMapper.map(order, OrderResponseDTO.class)).collect(Collectors.toList());
  }

}
