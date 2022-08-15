package id.mayhendrap.sitemap.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlGenerator {
	
  private XmlGenerator() {}
  
  public static void ofUrls(List<String> urls, String baseDir, String sitemapName, String priority) {
    String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element sitemapIndex = doc.createElement("urlset");
        sitemapIndex.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        sitemapIndex.setAttribute("xmlns:xsi", "http://www.sitemaps.org/schemas/sitemap/0.9");
        sitemapIndex.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 " +
                "http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
        doc.appendChild(sitemapIndex);

        for (String url : urls) {
            Element urlTag = doc.createElement("url");
            sitemapIndex.appendChild(urlTag);

            Element loc = doc.createElement("loc");
            loc.appendChild(doc.createTextNode(url));
            urlTag.appendChild(loc);

            Element lastMod = doc.createElement("lastmod");
            lastMod.appendChild(doc.createTextNode(dateTime));
            urlTag.appendChild(lastMod);

            Element priorityTag = doc.createElement("priority");
            priorityTag.appendChild(doc.createTextNode(priority));
            urlTag.appendChild(priorityTag);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        Path pathFile = Paths.get(baseDir + sitemapName + ".xml");
        StreamResult result = new StreamResult(new File(String.valueOf(pathFile)));

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        doc.setXmlStandalone(true);
        transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException e) {
        e.printStackTrace();
    }
}
  
}
