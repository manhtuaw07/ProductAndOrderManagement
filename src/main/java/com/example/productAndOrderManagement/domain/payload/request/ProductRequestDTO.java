package com.example.productAndOrderManagement.domain.payload.request;

import com.example.productAndOrderManagement.domain.dto.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

  private Long id;
  private String title;
  private Double price;
  private String description;
  private String category;
  private String imageLink;
  private String imageBase64;
  private Rating rating;
}
