package vyom.dunk.app.ui;

import java.util.Scanner;
import java.io.Console;
import java.io.FileReader;

import vyom.dunk.app.models.Enemy;
import vyom.dunk.app.models.Player;
import java.io.Reader;

public class Ui {
  private static String title = "Bienvenido a Dungeon Peru";
  private static String subtitle = "";
  private static boolean isLoggin = false;
  private static String user;

  static Scanner sc = new Scanner(System.in);

  public static void render() {
    clearScreen();
    System.out.println(title);
    if (subtitle != "")
      System.out.println(subtitle);

    System.out.println();
  }

  public static void home() {

    while (true) {
      render();
      System.out.println("1)Iniciar sesion.");
      System.out.println("2)Registrarse.");
      System.out.println("3)Idioma.");
      System.out.println("4)Salir.");

      System.out.print("Ingrese opcion: ");
      String opt = sc.nextLine().trim();

      switch (opt) {
        case "1" -> login();
        case "2" -> register();
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
    subtitle = "Login";
    render();

    System.out.print("Ingrese nickname: ");
    String nick = sc.nextLine().trim();

    user = nick;

    Console console = System.console();

    console.printf("Ingrese su contrasena: ");
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    console.printf(passwordString + "\n");

    isLoggin = true;

    if (true)
      menu2();
  }

  public static void register() {
    Console console = System.console();

    console.printf("Ingrese su nickname: ");
    String nickname = console.readLine();

    console.printf("Ingrese su correo(incluir @gmail): ");
    String email = console.readLine();

    console.printf("Ingrese su contrasena");
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    console.printf("Usuario creado!");
    console.printf(nickname + "|" + email);
  }

  static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // Si el inicio de sesion es exitoso muestra este menu
  static void menu2() {
    clearScreen();
    render();

    while (true) {
      clearScreen();
      render();
      System.out.println("1)Iniciar partida");
      System.out.println("2)Continuar partida");
      System.out.println("3)Volver al menu principal");

      System.out.print("Ingrese opcion: ");
      String opt = sc.nextLine().trim();

      switch (opt) {
        case "1" -> System.out.println("Iniciando partida...");
        case "2" -> System.out.println("Aun no esta disponible XD");
        case "3" -> {
          return;
        }
      }
    }
  }

  static void battle() {
    Player p1 = new Player(user, 100);

    System.out.println("===== La batalla comenzara ====");

  }
}
