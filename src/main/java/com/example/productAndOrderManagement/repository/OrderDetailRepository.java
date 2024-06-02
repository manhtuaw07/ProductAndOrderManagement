package com.example.productAndOrderManagement.repository;

import com.example.productAndOrderManagement.domain.model.OrderDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

  Optional<List<OrderDetail>> findByOrderId(Long orderId);
}
