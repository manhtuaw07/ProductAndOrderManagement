package com.example.productAndOrderManagement.domain.model;
import com.example.productAndOrderManagement.domain.dto.Rating;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "product")
@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private Double price;
  private String description;
  private String category;

  @Column(name = "image_link")
  private String imageLink;

  @Column(name = "image_base64")
  private String imageBase64;

  @Embedded
  private Rating rating;
}
