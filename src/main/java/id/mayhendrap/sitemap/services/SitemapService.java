package id.mayhendrap.sitemap.services;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.mayhendrap.sitemap.models.Product;
import id.mayhendrap.sitemap.utils.UrlGenerator;
import id.mayhendrap.sitemap.utils.XmlGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SitemapService {

  public void generateSitemap() {
    log.info("DOMAIN - GENERATING SITEMAP");
    List<Product> products = getAllProducts();
    LinkedList<String> urls = new LinkedList<>();

    for (Product product : products) {      
      String newUrl = UrlGenerator.newUrl(product.getId(), product.getName());
      urls.add("https://domain.com" + newUrl);
    }

    XmlGenerator.ofUrls(urls, "C:\\sitemap\\", "sitemap-products", "0.8");
  }

  private List<Product> getAllProducts() {
    log.info("DOMAIN - GETTING PRODUCTS");
    List<Product> products = new LinkedList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      products = objectMapper.readValue(new FileReader("example-data.json", StandardCharsets.UTF_8),
          new TypeReference<>(){});
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return products;
  }

}
