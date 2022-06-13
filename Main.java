import java.io.IOException;
import java.util.Date;

import webparse.WebUtils;
import webparse.FileUtils;
import webparse.WebParseException;

public class Main {

  // Configure it for number of parallel runnables
  private final static int N = 2;

  // Configure it for maximum time to process all threads.
  private final static int MAX_TIMEOUT = 100;

  // Set to true if you need to read words present in tags and attributes.
  public static boolean includeTagsAndAttributes = false;

  public static void main(String[] args) throws Exception {
    try {
      WebUtils w = new WebUtils("https://entromy.com", Main.includeTagsAndAttributes);
      w.parseWebPage();
      w.searchAllWords(N, MAX_TIMEOUT);
      FileUtils f = new FileUtils();
      f.saveInFile("out_" + new Date().getTime() + ".json", w.getJSONObject());
    } catch (WebParseException e) {
      System.out.println("Error parsing the url");
    } catch (InterruptedException e) {
      System.out.println("Threads Interrupted");
    } catch (IOException e) {
      System.out.println("Error writting in file");
    }
  }

}
