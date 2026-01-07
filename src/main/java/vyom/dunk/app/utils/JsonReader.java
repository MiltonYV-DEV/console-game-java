package vyom.dunk.app.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JsonReader {
  // private final String name;
  // private final String description;
  // private final String dialogs;
  // private final String nameAttack;
  public static void test() throws IOException {
    ObjectMapper ob = new ObjectMapper();
    JsonNode jn = ob.readTree(new File("src/main/java/vyom/dunk/app/utils/TestEnemy.json"));
    String name = jn.get("name").asText();

    System.out.println(name);
    Scanner scan = new Scanner(System.in);
    scan.nextLine();
    scan.close();
  }
}
