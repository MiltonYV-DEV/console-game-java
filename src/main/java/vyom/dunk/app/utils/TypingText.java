package vyom.dunk.app.utils;

public class TypingText {

  public static void slowPrint(String[] elements, long delay) {
    for (String text : elements) {
      for (char character : text.toCharArray()) {
        System.out.print(character);
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          e.printStackTrace();
        }
      }
      // System.out.println();

    }
    // System.out.println();
  }

  public static void printText(String[] elements, int speed) {
    // String[] elements = { "YO SOY TITULO", "yo soy parrafo" };
    // long speed = 50;

    slowPrint(elements, speed);
  }
}
