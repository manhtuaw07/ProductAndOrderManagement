import com.example.productAndOrderManagement.controller.ProductController;
import com.example.productAndOrderManagement.domain.exception.ResourceNotFoundException;
import com.example.productAndOrderManagement.domain.payload.request.ProductRequestDTO;
import com.example.productAndOrderManagement.domain.payload.response.ProductResponseDTO;
import com.example.productAndOrderManagement.service.ProductService;
import com.example.productAndOrderManagement.domain.validator.ProductRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @Mock
  private ProductRequestValidator productRequestValidator;

  @Mock
  private BindingResult bindingResult;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

//  @Test
//  public void addProductSuccessfully() {
//    ProductRequestDTO productRequest = new ProductRequestDTO();
//    when(bindingResult.hasErrors()).thenReturn(false);
//    when(productService.addProduct(productRequest)).thenReturn("Product added successfully");
//
//    ResponseEntity<?> response = productController.addProduct(productRequest, bindingResult);
//
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals("Product added successfully", response.getBody());
//  }

  @Test
  public void addProductValidationFailed() {
    ProductRequestDTO productRequest = new ProductRequestDTO();
    when(bindingResult.hasErrors()).thenReturn(true);

    ResponseEntity<?> response = productController.addProduct(productRequest, bindingResult);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void getProductByIdSuccessfully() {
    Long id = 1L;
    ProductResponseDTO productResponse = new ProductResponseDTO();
    when(productService.getProductById(id)).thenReturn(productResponse);

    ResponseEntity<?> response = productController.getProductById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(productResponse, response.getBody());
  }

  @Test
  public void getProductByIdNotFound() {
    Long id = 1L;
    when(productService.getProductById(id)).thenThrow(ResourceNotFoundException.class);

    ResponseEntity<?> response = productController.getProductById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}