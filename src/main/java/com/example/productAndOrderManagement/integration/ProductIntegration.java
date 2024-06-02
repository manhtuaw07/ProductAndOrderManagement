package com.example.productAndOrderManagement.integration;

import com.example.productAndOrderManagement.domain.dto.FakeStoreProduct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductIntegration {
  private final RestTemplate restTemplate;
  private final Logger logger = Logger.getLogger(ProductIntegration.class.getName());

  public List<FakeStoreProduct> getAllProducts() {
    FakeStoreProduct[] products;
    try {
      String url = "https://fakestoreapi.com/products";
      products = restTemplate.getForObject(url, FakeStoreProduct[].class);
    } catch (RestClientException e) {
      logger.warning("Error while fetching products from FakeStore API");
      return new ArrayList<>();
    }
    if (products == null) {
      return new ArrayList<>();
    }
    logger.info("Successfully fetched products from FakeStore API:" + products.length);
    return List.of(products);
  }
}
