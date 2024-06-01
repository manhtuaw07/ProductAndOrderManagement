package com.example.productAndOrderManagement.repository.specification;

import com.example.productAndOrderManagement.model.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

  public static Specification<Order> customerNameContains(String customerName) {
    return (root, query, criteriaBuilder) -> {
      if (customerName == null || customerName.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")), "%" + customerName.toLowerCase() + "%");
    };
  }

  public static Specification<Order> orderIdContains(Long orderId) {
    return (root, query, criteriaBuilder) -> {
      if (orderId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("id"), orderId);
    };

  }

}
