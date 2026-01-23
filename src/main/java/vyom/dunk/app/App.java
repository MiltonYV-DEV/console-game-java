package vyom.dunk.app;

import java.io.IOException;

import vyom.dunk.app.clients.EnemyMicroserviceClient;
import vyom.dunk.app.config.Database;
import vyom.dunk.app.repositories.BattleRepository;
import vyom.dunk.app.repositories.CharacterRepository;
import vyom.dunk.app.repositories.GeneratedEnemyRepository;
import vyom.dunk.app.repositories.HistoryRepository;
import vyom.dunk.app.repositories.MatchRepository;
import vyom.dunk.app.repositories.UserRepository;
import vyom.dunk.app.services.GameService;
import vyom.dunk.app.services.HistoryService;
import vyom.dunk.app.services.MatchService;
import vyom.dunk.app.services.UserService;
import vyom.dunk.app.ui.Ui;

public class App {
  public static void main(String[] args) throws IOException {

    Database db = new Database("jdbc:mysql://localhost:3306/poo_pf", "root", "12345");
    String baseUrl = "http://127.0.0.1:8000";

    UserRepository userRepo = new UserRepository();
    CharacterRepository charRepo = new CharacterRepository();
    GeneratedEnemyRepository genRepo = new GeneratedEnemyRepository();
    BattleRepository battleRepo = new BattleRepository();
    CharacterRepository characterRepo = new CharacterRepository();
    HistoryRepository historyRepo = new HistoryRepository();
    MatchRepository matchRepo = new MatchRepository();

    EnemyMicroserviceClient enemyClient = new EnemyMicroserviceClient(baseUrl);

    UserService userService = new UserService(db, userRepo, charRepo);
    MatchService matchService = new MatchService(db, matchRepo);
    GameService gameService = new GameService(db, matchRepo, genRepo, battleRepo, characterRepo, enemyClient);
    HistoryService historyService = new HistoryService(db, historyRepo);

    Ui ui = new Ui(userService, matchService, gameService, historyService, enemyClient);

    ui.home();
  }
}
