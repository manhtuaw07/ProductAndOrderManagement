import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.productAndOrderManagement.domain.dto.FakeStoreProduct;
import com.example.productAndOrderManagement.integration.ProductIntegration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class CommonTest {
//write unit tests for public List<FakeStoreProduct> getAllProducts() in ProductIntegration.java
  @Test
  void getAllProducts() {
    //Arrange
    ProductIntegration productIntegration = new ProductIntegration(new RestTemplate());
    //Act
    List<FakeStoreProduct> products = productIntegration.getAllProducts();
    //Assert
    assertNotNull(products);
  }
}
