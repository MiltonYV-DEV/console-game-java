package vyom.dunk.app.ui;

import java.util.Scanner;
import java.io.Console;
import vyom.dunk.app.models.Player;
import vyom.dunk.app.utils.TypingText;

public class Ui {
  private static boolean isLoggin = false;
  private static String user;

  static Scanner sc = new Scanner(System.in);

  public static void render() {
    clearScreen();
  }

  public static void home() {

    while (true) {
      render();
      String[] elementsHome = { "BIENVENIDO A PERUDUNGEON\n", "1)Iniciar sesion\n", "2)Registrarse\n",
          "3)Crear usuarios ranking(test db)\n", "5)Idioma\n", "6)Salir\n" };

      TypingText.printText(elementsHome, 25);

      String opt = readOpt();

      switch (opt) {
        case "1" -> login();
        case "2" -> register();
        case "3" -> System.out.println("No hay mas idiomas disponibles XD");
        case "4" -> System.out.println("No hay mas idiomas disponibles XD");
        case "5" -> System.out.println("No hay mas idiomas disponibles XD");
        case "6" -> {
          System.out.println("Saliendo...");
          return;
        }
        default -> System.out.println("Dato incorrecto, vuelva a intentar");
      }
    }
  }

  public static void login() {
    clearScreen();
    String[] elementsLogin = { "Login\n", "Ingrese su nickname: " };

    TypingText.printText(elementsLogin, 25);

    String nick = sc.nextLine().trim();

    Console console = System.console();

    String[] elementsLoginPass = { "Ingrese contrasena: " };
    TypingText.printText(elementsLoginPass, 25);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    console.printf(passwordString + "\n");

    isLoggin = true;

    if (true)
      menu2();
  }

  public static void register() {
    Console console = System.console();

    String[] registerElementNick = { "Ingrese su nickname: " };
    TypingText.printText(registerElementNick, 25);
    String nickname = console.readLine();

    String[] registerElementEmail = { "Ingrese su correo(incluir @gmail): " };
    TypingText.printText(registerElementEmail, 25);
    String email = console.readLine();

    String[] registerElementPass = { "Ingrese su contrasena" };
    TypingText.printText(registerElementPass, 25);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    console.printf("Usuario creado!");
    console.printf(nickname + "|" + email);
  }

  static void menu2() {
    clearScreen();
    render();

    while (true) {
      clearScreen();

      String[] menu2Elements = { "1)Iniciar partida\n", "2)ver ultimas partidas\n", "3)Ver ranking mundial\n",
          "4)Volver al menu principal\n" };
      TypingText.printText(menu2Elements, 25);

      String opt = readOpt();

      switch (opt) {
        case "1" -> System.out.println("Iniciando partida...");
        case "2" -> System.out.println("Aun no esta disponible XD");
        case "3" -> System.out.println("Cargando ranking");
        case "4" -> {
          return;
        }

      }
    }
  }

  static void battle() {
    Player p1 = new Player(user, 100);
    System.out.println("===== La batalla comenzara ====");

  }

  static String readOpt() {
    String[] readElement = { "Ingrese una opcion: " };
    TypingText.printText(readElement, 25);
    String opt = sc.nextLine().trim();

    return opt;
  }

  static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
