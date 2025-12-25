package vyom.dunk.app.ui;

import java.util.Scanner;

import vyom.dunk.app.utils.WriteText;

public class Ui {
  private static String title = "Project x";

  static Scanner sc = new Scanner(System.in);

  public static void render() {
    clearScreen();
    System.out.println("Bienenido a " + title);
    System.out.println();
  }

  public static void home() {

    while (true) {
      render();
      System.out.println("1) Iniciar sesion.");
      System.out.println("2) Registrarse.");
      System.out.println("3) Idioma.");
      System.out.println("4) Salir.");

      System.out.print("Ingrese opcion: ");
      String opt = sc.nextLine().trim();

      switch (opt) {
        case "1" -> login();
        case "2" -> System.out.println("Conectando con la base de datos");
        case "3" -> System.out.println("No hay mas idiomas disponibles XD");
        case "4" -> {
          System.out.println("Saliendo...");
          return;
        }
        default -> System.out.println("Dato incorrecto, vuelva a intentar");
      }
    }
  }

  public static void login() {
    clearScreen();
    render();

    System.out.print("Ingrese nickname: ");
    String nick = sc.nextLine().trim();

    WriteText.write(nick);
    System.out.print("Ingrese contrasena: ");
    String password = sc.nextLine().trim();
  }

  public static void register() {
    System.out.print("Ingrese nickname: ");
    String nick = sc.nextLine().trim();
    System.out.println();
  }

  static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
