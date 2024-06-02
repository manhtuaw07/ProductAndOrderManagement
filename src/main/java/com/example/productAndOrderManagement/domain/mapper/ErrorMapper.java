package com.example.productAndOrderManagement.domain.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.ObjectError;

public class ErrorMapper {

  public static Map<String, String> errorsToMap(List<ObjectError> errors) {
    Map<String, String> errorMap = new HashMap<>();
    for (ObjectError error : errors) {
      String field = Objects.requireNonNull(error.getCodes())[0];
      String errorMessage = error.getDefaultMessage();
      errorMap.put(field, errorMessage);
    }
    return errorMap;
  }
}
