package id.mayhendrap.sitemap.utils;

public class UrlGenerator {
  
  private UrlGenerator() {}

  public static String newUrl(String name, String id) {
      String rawUrl = name + " " + id;
      String cleanUrl = rawUrl.toLowerCase().trim()
              .replaceAll("[^\\w ]+", "")
              .replaceAll(" +", "-");
      return "/" + cleanUrl;
  }
  
}
