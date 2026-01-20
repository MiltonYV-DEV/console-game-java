package vyom.dunk.app.ui;

import java.util.Scanner;
import java.io.Console;
import vyom.dunk.app.models.Player;
import vyom.dunk.app.resources.UserCreateRequest;
import vyom.dunk.app.services.UserService;
import vyom.dunk.app.utils.TypingText;

public class Ui {
  private boolean isLoggin = false;
  private String user;
  private final UserService service;

  static Scanner sc = new Scanner(System.in);

  public Ui(UserService service) {
    this.service = service;
  }

  public static void render() {
    clearScreen();
  }

  public void home() {

    while (true) {
      render();
      String[] elementsHome = { "BIENVENIDO A PERUDUNGEON\n", "1)Iniciar sesion\n", "2)Registrarse\n",
          "3)Crear usuarios ranking(test db)\n", "5)Idioma\n", "6)Salir\n" };

      TypingText.printText(elementsHome);

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

  public void login() {
    clearScreen();
    String[] elementsLogin = { "Login\n", "Ingrese su nickname: " };

    TypingText.printText(elementsLogin);

    String username = sc.nextLine().trim();

    Console console = System.console();

    String[] elementsLoginPass = { "Ingrese contrasena: " };
    TypingText.printText(elementsLoginPass);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    console.printf(passwordString + "\n");

    // isLoggin = true;

    if (true)
      menu2();
  }

  public void register() {
    clearScreen();
    Console console = System.console();

    String[] registerElementNick = { "BIENVENIDO AL REGISTRO\n", "Ingrese su nickname: " };
    TypingText.printText(registerElementNick);
    String username = console.readLine();

    String[] registerElementPass = { "Ingrese su contrasena" };
    TypingText.printText(registerElementPass);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    try {
      long id = service.createUser(new UserCreateRequest(username, passwordString));
      // System.out.println("Usuario creado con id: " + id);
      String[] registerElementUserCreated = { "Usuario creado con id: " + id + "\n" };
      TypingText.printText(registerElementUserCreated);
      readContinue();

    } catch (Exception e) {
      // System.out.println("Error: " + e.getMessage());
      String[] registerElementException = { "Este username ya existe\n" };
      TypingText.printText(registerElementException);
      readContinue();
    }

  }

  public void menu2() {
    clearScreen();
    render();

    while (true) {
      clearScreen();

      String[] menu2Elements = { "1)Iniciar partida\n", "2)ver ultimas partidas\n", "3)Ver ranking mundial\n",
          "4)Volver al menu principal\n" };
      TypingText.printText(menu2Elements);

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

  public void battle() {
    // Player p1 = new Player(this.user, 100);
    System.out.println("Contra quien quieres pelear?");

  }

  public String readOpt() {
    String[] readElement = { "Ingrese una opcion: " };
    TypingText.printText(readElement);
    String opt = sc.nextLine().trim();

    return opt;
  }

  public String readContinue() {
    String[] continueElement = { "Ingrese cualquier letra para continuar: " };
    TypingText.printText(continueElement);
    String rd = sc.nextLine().trim();
    return rd;
  }

  static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
