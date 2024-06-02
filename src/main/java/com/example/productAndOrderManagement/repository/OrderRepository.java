package com.example.productAndOrderManagement.repository;

import com.example.productAndOrderManagement.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
    JpaSpecificationExecutor<Order> {

  Page<Order> findByCustomerNameContainingAndId(String customerName, Long id,
      Pageable pageable);

  Page<Order> findById(Long id, Pageable pageable);

}
