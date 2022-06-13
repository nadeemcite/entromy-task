package webparse;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

public class FileUtils {

  public static void saveInFile(String fileName, String content) throws Exception {
    try (Writer writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
      writer.write(content);
    } catch (IOException ex) {
      throw new Exception("Error writing file.");
    }
  }
}