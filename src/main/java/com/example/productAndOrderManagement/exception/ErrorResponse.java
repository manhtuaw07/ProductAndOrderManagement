package com.example.productAndOrderManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
  private HttpStatus status;
  private String message;
  private String details;
}