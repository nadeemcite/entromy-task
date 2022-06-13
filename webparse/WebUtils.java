package webparse;

import java.util.Map;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import org.json.JSONObject;

public class WebUtils {

  private String url;

  private Elements allElements;

  private final Map<String, Integer> wordCount;

  private final boolean includeTagsAndAttributes;

  public WebUtils(String url, boolean includeTagsAndAttributes) {
    this.wordCount = new HashMap<String, Integer>();
    this.url = url;
    this.includeTagsAndAttributes = includeTagsAndAttributes;
  }

  public void parseWebPage() throws WebParseException {
    try {
      Document document = Jsoup.connect(this.url).get();
      this.allElements = document.getAllElements();
    } catch (IOException e) {
      throw new WebParseException();
    }
  }

  public void searchAllWords(int maxThreads, int maxTimeoutSeconds) throws InterruptedException {

    ExecutorService es = Executors.newFixedThreadPool(maxThreads);
    for (Element element : allElements) {
      es.submit(new HTMLElement(element, this.wordCount, this.includeTagsAndAttributes));
    }
    es.shutdown();
    es.awaitTermination(maxTimeoutSeconds, TimeUnit.SECONDS);

  }

  public String getJSONObject() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("words", this.wordCount);
    return jsonObject.toString();
  }
}