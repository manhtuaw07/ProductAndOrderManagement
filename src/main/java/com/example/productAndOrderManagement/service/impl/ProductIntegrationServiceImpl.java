package com.example.productAndOrderManagement.service.impl;

import com.example.productAndOrderManagement.domain.dto.FakeStoreProduct;
import com.example.productAndOrderManagement.domain.dto.InternalProduct;
import com.example.productAndOrderManagement.domain.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.integration.ProductIntegration;
import com.example.productAndOrderManagement.service.ProductService;
import com.example.productAndOrderManagement.service.ThirdPartyIntegrationService;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductIntegrationServiceImpl implements ThirdPartyIntegrationService {

  private static final Logger logger = LoggerFactory.getLogger(ProductIntegrationServiceImpl.class);
  private final ProductIntegration productIntegration;
  private final ProductService productService;
  private final ModelMapper modelMapper;


  @Override
  public void fetchAndSaveProducts() {
    List<InternalProduct> allProducts = getAllProductsFromFakeAPI();
    if (allProducts.isEmpty()) {
      return;
    }

    List<ProductRequestDTO> productRequestDTOList = allProducts.stream()
        .map(product -> modelMapper.map(product, ProductRequestDTO.class))
        .collect(Collectors.toList());
    productService.addAllProducts(productRequestDTOList);
  }

  private List<InternalProduct> getAllProductsFromFakeAPI() {
    List<FakeStoreProduct> allProducts = productIntegration.getAllProducts();
    if (allProducts.isEmpty()) {
      return List.of();
    }
    return convertProductsToInternalWithBase64Image(allProducts);
  }

  //convert image in FakeStoreProduct to base64
  private List<InternalProduct> convertProductsToInternalWithBase64Image(List<FakeStoreProduct> allProducts) {
    return allProducts.stream().map(product -> {
      InternalProduct internalProduct = new InternalProduct();
      internalProduct.setId(product.getId());
      internalProduct.setTitle(product.getTitle());
      internalProduct.setPrice(product.getPrice());
      internalProduct.setDescription(product.getDescription());
      internalProduct.setCategory(product.getCategory());
      internalProduct.setRating(product.getRating());

      if (StringUtils.isNotEmpty(product.getImageLink()) && product.getImageLink().startsWith("data:image")) {
        return internalProduct;
      }
      try {
        internalProduct.setImageLink(product.getImageLink());
        internalProduct.setImageBase64(Base64.getEncoder().encodeToString(product.getImageLink().getBytes()));
      } catch (Exception e) {
        internalProduct.setImageBase64(null);
        logger.error("Error while converting image to base64", e);
      }
      return internalProduct;
    }).collect(Collectors.toList());
  }
}
