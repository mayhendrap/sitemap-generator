package id.mayhendrap.sitemap.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import id.mayhendrap.sitemap.services.SitemapService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1")
public class SitemapController {

  private final SitemapService sitemapService;

  @GetMapping(path = "/sitemap")
  public void generateSitemap() {
    sitemapService.generateSitemap();
  }

}
