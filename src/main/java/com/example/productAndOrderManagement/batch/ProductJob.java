package com.example.productAndOrderManagement.batch;

import com.example.productAndOrderManagement.service.ThirdPartyIntegrationService;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductJob {

  private final ThirdPartyIntegrationService thirdPartyIntegrationService;
  private final Logger logger = Logger.getLogger(ProductJob.class.getName());
  private final static String CRON_EXPRESSION = "0 0 2 * * ?";

  @Scheduled(cron = CRON_EXPRESSION)
  public void fetchProductsJob() {
    thirdPartyIntegrationService.fetchAndSaveProducts();
    logger.info("Fetched and saved products from FakeStore API");
  }

}
