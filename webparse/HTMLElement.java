package webparse;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Attribute;

public class HTMLElement implements Runnable {

  private final Element e;
  private final Map<String, Integer> wordCount;
  private final boolean includeTagsAndAttributes;

  public HTMLElement(Element e, Map<String, Integer> wordCount, boolean includeTagsAndAttributes) {
    this.e = e;
    this.wordCount = wordCount;
    this.includeTagsAndAttributes = includeTagsAndAttributes;
  }

  private void addOrIncrement(String word) {
    String singleCaseWord = word.toLowerCase();
    Integer prevCount = this.wordCount.get(singleCaseWord);
    if (prevCount == null) {
      this.wordCount.put(singleCaseWord, 1);
    } else {
      this.wordCount.put(singleCaseWord, prevCount + 1);
    }
  }

  public void run() {
    String textWithoutSpecialChars = this.e.ownText().replaceAll("[^a-zA-Z]", " ").trim();
    for (String word : textWithoutSpecialChars.split(" ")) {
      if (word != "") {
        this.addOrIncrement(word);
      }
    }
    if (this.includeTagsAndAttributes) {
      this.addOrIncrement(this.e.nodeName());
      List<Attribute> attrs = this.e.attributes().asList();
      for (Attribute attr : attrs) {
        this.addOrIncrement(attr.getKey());
        String attrTextWithoutSpecialChars = attr.getValue().replaceAll("[^a-zA-Z]", " ").trim();
        for (String word : attrTextWithoutSpecialChars.split(" ")) {
          if (word != "") {
            this.addOrIncrement(word);
          }
        }
      }
    }
  }

}