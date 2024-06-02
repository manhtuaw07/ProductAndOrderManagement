package com.example.productAndOrderManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FakeStoreProduct {
  private Long id;
  private String title;
  private Double price;
  private String description;
  private String category;
  @JsonProperty("image")
  private String imageLink;
  private Rating rating;
}
