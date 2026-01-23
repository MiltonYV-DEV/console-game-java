package vyom.dunk.app.ui;

import java.util.Scanner;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.awt.Transparency;
import java.io.Console;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Connection;

import vyom.dunk.app.clients.EnemyMicroserviceClient;
import vyom.dunk.app.game.BattleEngine;
import vyom.dunk.app.resources.CombatStats;
import vyom.dunk.app.resources.EnemyPayload;
import vyom.dunk.app.resources.LoginDTO;
import vyom.dunk.app.resources.LoginResponseDTO;
import vyom.dunk.app.resources.MatchFinishDTO;
import vyom.dunk.app.resources.ProfileDTO;
import vyom.dunk.app.resources.RegisterDTO;
import vyom.dunk.app.resources.RegisterResponseDTO;
import vyom.dunk.app.services.GameService;
import vyom.dunk.app.services.HistoryService;
import vyom.dunk.app.services.MatchService;
import vyom.dunk.app.services.StatBalancer;
import vyom.dunk.app.services.UserService;
import vyom.dunk.app.utils.TypingText;

public class Ui {
  private boolean isLoggin = false;
  private String username;
  private final UserService userService;
  private final MatchService matchService;
  private final StatBalancer statBalancer = new StatBalancer();
  private final BattleEngine battleEngine = new BattleEngine();
  private final GameService gameService;
  private final HistoryService historyService;
  private final EnemyMicroserviceClient enemyClient;
  long userId;

  static final Scanner sc = new Scanner(System.in);
  Console console = System.console();

  public Ui(UserService userService, MatchService matchService, GameService gameService,
      HistoryService historyService, EnemyMicroserviceClient enemyClient) {
    this.userService = userService;
    this.matchService = matchService;
    this.gameService = gameService;
    this.historyService = historyService;
    this.enemyClient = enemyClient;
  }

  public static void render() {
    clearScreen();
  }

  public void home() {

    while (true) {
      render();
      String[] homeElements = { "BIENVENIDO A YOUBATTLE\n", "Version beta 0.5\n\n", "1)Iniciar sesion\n",
          "2)Registrarse\n",
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
          sc.close();
          shutdownMySqlCleanup();
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

    String[] elementsLoginPass = { "Ingrese contrasena: " };
    TypingText.printText(elementsLoginPass);
    char[] passwordChars = console.readPassword();
    String passwordString = new String(passwordChars);

    try {
      LoginDTO dto = new LoginDTO(username, passwordString);
      LoginResponseDTO res = userService.login(dto);
      username = res.username();
      userId = res.userId();
      String[] elementLoginUser = { "Bienvenid@ aventurer@ " + res.username() + "\n",
          "Que cada una de tus batallas sea legendaria!\n" };

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
        case "1" -> startMatch(userId);
        case "2" -> userProfile();
        case "3" -> viewHistory(userId);
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
    clearScreen();
    try {
      String[] startMatchText = { "¿Contra quién quieres pelear?\n" };
      TypingText.printText(startMatchText);

      String prompt = sc.nextLine().trim();

      String[] loading = { "\nLOADING...\n", "Tu enememigo se esta creado..." };
      TypingText.printText(loading);

      gameService.playMatch(userId, prompt, sc);

    } catch (Exception e) {
      String[] errorStartMatch = { "Error: " + e.getMessage() + "\n" };
      TypingText.printText(errorStartMatch);
    }

    readContinue();
  }

  private void viewHistory(long userId) {
    clearScreen();
    try {
      var list = historyService.getLastMatches(userId, 5);

      String[] viewHistorytitle = { "HISTORIAL" + "(últimas 5)" + "\n",
          "---------------------------------\n",
      };
      TypingText.printText(viewHistorytitle);

      if (list.isEmpty()) {
        String[] viewHisoryEmpty = { "No tienes partidas registradas." + "\n" };
        return;
      }

      for (var row : list) {
        String enemy = (row.enemyName() == null) ? "N/A" : row.enemyName();
        String ended = (row.endedAt() == null) ? "EN CURSO" : row.endedAt().toString();

        String[] historyList = {
            "Match_id: " + row.matchId() + " | " + row.startedAt() + " -> " + row.endedAt() + "\n",
            "Resultado: " + row.result() + " | Turnos: " + row.totalTurns() + " | XP: +" + row.xpGained() + "\n",
            "Enemigo: " + enemy + "\n",
            "Daño hecho: " + row.damageDealt() + " | Daño recibido: " + row.damageTaken() + "\n",
            "---------------------------------\n"
        };

        TypingText.printText(historyList);
      }
    } catch (Exception e) {
      String[] errorViewHistory = { "Error mostando historial: " + e.getMessage() };
      TypingText.printText(errorViewHistory);
    }

    readContinue();
  }

  private String getSampleEnemyJson() {
    return """
            {
            "enemy": {
              "name": "Alan García",
              "alias": "El Cabezón Destrozador",
              "description": "Entrando en la escena con una chaqueta sospechosamente brillante y una sonrisa seductora, Alan García se acomoda la corbata mientras murmura: 'Hoja de ruta, hoja de ruta...'. A medida que avanza, palpita la emoción, como si fuera a explicar su plan económico. De repente, suelta un grito que parece un eco perdido: '¡Que empiece la pelea, pero que sea en un programa de televisión!'. Todos se preguntan si su estrategia finalmente dará resultados.",
              "opening_dialogue": "¡¿Qué pasa, joven?! ¡Llegaste justo a tiempo para un debate!"
            },
            "enemy_attacks": [
              {
                "name": "Promesa de Reforma",
                "damage": 15,
                "description": "Un golpe directo con un proyecto de ley que nadie quería, pero todos siguen debatiendo."
              },
              {
                "name": "Discurso Ilusorio",
                "damage": 20,
                "description": "Alan comienza a hablar de sus grandes logros con tanta pasión que te deja aturdido."
              },
              {
                "name": "Cero Supervisión",
                "damage": 25,
                "description": "Un ataque desastroso donde asegura que todos se encargan del trabajo y nadie lo supervisa: ¡impactante!"
              }
            ],
            "player_attacks": [
              {
                "name": "Día de la No-Corrupción",
                "damage": 10,
                "description": "Recuerdas ese día que prometió que todo estaba limpio... ¡ja, qué risa!"
              },
              {
                "name": "El Caso Olvidado",
                "damage": 15,
                "description": "Mencionas ese escándalo que todo el mundo olvida, pero que le hace cambiar de color."
              },
              {
                "name": "La Entrevista Chusca",
                "damage": 20,
                "description": "Le lanzas el meme de la entrevista donde se enreda con las palabras y te ríes a carcajadas."
              }
            ]
          }
        """;
  }

  public String readOpt() {
    String[] readElement = { "Ingrese una opcion: " };
    TypingText.printText(readElement);
    String opt = sc.nextLine().trim();

    return opt;
  }

  public static String readContinue() {
    String[] continueElement = { "Ingrese cualquier letra para continuar: " };
    TypingText.printText(continueElement);

    String rc = sc.nextLine().trim();
    return rc;
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void shutdownMySqlCleanup() {
    try {
      AbandonedConnectionCleanupThread.checkedShutdown();
    } catch (Exception ignored) {
    }
  }
}
