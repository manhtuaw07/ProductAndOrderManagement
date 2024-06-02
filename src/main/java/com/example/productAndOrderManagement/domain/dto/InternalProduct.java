package com.example.productAndOrderManagement.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalProduct extends FakeStoreProduct{
  private String imageBase64;
}
