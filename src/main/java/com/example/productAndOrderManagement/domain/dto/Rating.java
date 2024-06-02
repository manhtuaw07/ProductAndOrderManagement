package com.example.productAndOrderManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {

  @JsonProperty("rate")
  private int ratingRate;

  @JsonProperty("count")
  private int ratingCount;
}
