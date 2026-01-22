package vyom.dunk.app.ui;

import java.util.Scanner;
import java.io.Console;
import java.sql.Connection;

import vyom.dunk.app.resources.LoginDTO;
import vyom.dunk.app.resources.LoginResponseDTO;
import vyom.dunk.app.resources.MatchFinishDTO;
import vyom.dunk.app.resources.ProfileDTO;
import vyom.dunk.app.resources.RegisterDTO;
import vyom.dunk.app.resources.RegisterResponseDTO;
import vyom.dunk.app.services.MatchService;
import vyom.dunk.app.services.UserService;
import vyom.dunk.app.utils.TypingText;

public class Ui {
  private boolean isLoggin = false;
  private String username;
  private final UserService userService;
  private final MatchService matchService;
  long userId;

  static Scanner sc = new Scanner(System.in);

  public Ui(UserService userService, MatchService matchService) {
    this.userService = userService;
    this.matchService = matchService;
  }

  public static void render() {
    clearScreen();
  }

  public void home() {

    while (true) {
      render();
      String[] homeElements = { "BIENVENIDO A PERUDUNGEON\n", "1)Iniciar sesion\n", "2)Registrarse\n",
          "3)Crear usuarios ranking(test db)\n", "5)Idioma\n", "6)Salir\n" };

      TypingText.printText(homeElements);

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
    String[] elementsLogin = { "LOGIN\n", "Ingrese su username: " };

    TypingText.printText(elementsLogin);

    String username = sc.nextLine().trim();

    Console console = System.console();

    String[] elementsLoginPass = { "Ingrese contrasena: " };
    TypingText.printText(elementsLoginPass);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    try {
      LoginDTO dto = new LoginDTO(username, passwordString);
      LoginResponseDTO res = userService.login(dto);
      username = res.username();
      userId = res.userId();
      String[] elementLoginUser = { "Bienvenido aventurero " + res.username() + "\n",
          "Que cada una de tus batallas sean legendarias!\n" };

      clearScreen();
      TypingText.printText(elementLoginUser);

      readContinue();
      menu2();
    } catch (Exception e) {
      String[] elementLoginUserError = { "Error: " + e.getMessage() + "\n" };
      TypingText.printText(elementLoginUserError);
      readContinue();
    }
  }

  public void register() {
    clearScreen();
    Console console = System.console();

    String[] registerElementNick = { "BIENVENIDO AL REGISTRO\n", "Ingrese su nickname: " };
    TypingText.printText(registerElementNick);
    String username = console.readLine();

    String[] registerElementPass = { "Ingrese su contraseña: " };
    TypingText.printText(registerElementPass);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    String[] registerElementCharacter = { "Ingrese nombre de su personaje: " };
    TypingText.printText(registerElementCharacter);
    String characterName = console.readLine();

    try {
      RegisterDTO dto = new RegisterDTO(username, passwordString, characterName);
      RegisterResponseDTO res = userService.register(dto);

      String[] registerElementUserCreated = { "Usuario creado con id: " + res.userId() + "\n" };
      TypingText.printText(registerElementUserCreated);

      readContinue();
    } catch (Exception e) {
      String[] registerElementException = { "Error: " + e + "\n" };
      TypingText.printText(registerElementException);
      readContinue();
    }

  }

  public void menu2() {
    clearScreen();
    render();

    while (true) {
      clearScreen();

      String[] menu2Elements = { "1)Iniciar partida\n", "2)Ver perfil\n", "3)Ver ultimas partidas\n",
          "4)Volver al menu principal\n" };
      TypingText.printText(menu2Elements);

      String opt = readOpt();

      switch (opt) {
        case "1" -> System.out.println("Iniciando partida...");
        case "2" -> userProfile();
        case "3" -> System.out.println("Cargando ranking");
        case "4" -> {
          return;
        }
      }
    }
  }

  public void userProfile() {
    clearScreen();
    render();

    try {
      ProfileDTO p = userService.getProfile(userId);

      String[] userProfileElements = {
          "PERFIL\n",
          "Usuario: " + p.username() + " (ID: " + p.userId() + ")" + "\n",
          "Personaje: " + p.characterName() + " (ID: " + p.characterId() + ")" + "\n",
          "Nivel: " + p.level() + "\n",
          "HP: " + p.hp() + "\n",
          "ATK: " + p.attack() + "\n",
          "DEF: " + p.defense() + "\n"
      };

      TypingText.printText(userProfileElements);

      readContinue();
    } catch (Exception e) {
      String[] userProfileError = { "Error: " + e.getMessage() + "\n" };
      TypingText.printText(userProfileError);

      readContinue();
    }
  }

  public void startMatch(long userId) {
    try {
      var start = matchService.startMatch(userId);
      long matchId = start.matchId();

      System.out.println("Partida iniciada. matchId=" + matchId);

      // TEST
      int turns = 3;
      String result = "WIN";
      int xp = 15;

      matchService.finishMatch(matchId, new MatchFinishDTO(result, turns, xp));

      System.out.println("Partida finalizada: " + result + " | turnos=" + turns + " | xp=" + xp);

      readContinue();

    } catch (Exception e) {
      System.out.println("Error en iniciar partida: " + e.getMessage());
    }
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
