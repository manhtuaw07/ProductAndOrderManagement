package com.example.productAndOrderManagement.domain.payload.response;

import com.example.productAndOrderManagement.domain.dto.Rating;
import javax.persistence.Column;
import javax.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

  private Long id;
  private String title;
  private Double price;
  private String description;
  private String category;
  private String imageLink;
  private String imageBase64;
  private Rating rating;
}
