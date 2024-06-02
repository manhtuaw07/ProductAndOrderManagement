package com.example.productAndOrderManagement.repository.specification;

import com.example.productAndOrderManagement.domain.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

  public static Specification<Product> nameContains(String keyword) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    };
  }

  public static Specification<Product> descriptionContains(String keyword) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%");
    };
  }
}
