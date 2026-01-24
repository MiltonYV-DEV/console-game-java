package vyom.dunk.app.services;

import java.sql.Connection;

import vyom.dunk.app.clients.EnemyMicroserviceClient;
import vyom.dunk.app.config.Database;
import vyom.dunk.app.game.BattleEngine;
import vyom.dunk.app.repositories.BattleRepository;
import vyom.dunk.app.repositories.CharacterRepository;
import vyom.dunk.app.repositories.GeneratedEnemyRepository;
import vyom.dunk.app.repositories.MatchRepository;
import vyom.dunk.app.resources.BattleResultDTO;
import vyom.dunk.app.resources.CombatStats;
import vyom.dunk.app.resources.EnemyPayload;
import vyom.dunk.app.resources.ProfileDTO;
import vyom.dunk.app.utils.JsonUtil;
import vyom.dunk.app.utils.TypingText;

public class GameService {
  private final Database db;
  private final MatchRepository matchRepo;
  private final GeneratedEnemyRepository genRepo;
  private final BattleRepository battleRepo;
  private final CharacterRepository characterRepo;

  private final StatBalancer statBalancer = new StatBalancer();
  private final BattleEngine battleEngine = new BattleEngine();

  private final EnemyMicroserviceClient enemyClient;

  public GameService(Database db,
      MatchRepository matchRepo,
      GeneratedEnemyRepository genRepo,
      BattleRepository battleRepo,
      CharacterRepository characterRepo, EnemyMicroserviceClient enemyClient) {
    this.db = db;
    this.matchRepo = matchRepo;
    this.genRepo = genRepo;
    this.battleRepo = battleRepo;
    this.characterRepo = characterRepo;
    this.enemyClient = enemyClient;
  }

  public void playMatch(long userId, String promptUserText, java.util.Scanner sc) throws Exception {
    try (Connection conn = db.getConnection()) {
      boolean old = conn.getAutoCommit();
      conn.setAutoCommit(false);

      long matchId;

      try {
        matchId = matchRepo.createMatch(conn, userId);

        ProfileDTO profile = characterRepo.selectProfileById(conn, userId)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró perfil"));

        CombatStats playerStats = new CombatStats(profile.hp(), profile.attack(), profile.attack());

        String enemyJson = enemyClient.generateEnemyJson(promptUserText);

        String[] enemyCreated = { "Estas listo? tu enemigo fue creado... muajajaja\n" };
        TypingText.printText(enemyCreated);

        EnemyPayload payload = JsonUtil.parseEnemyPayload(enemyJson);

        CombatStats enemyStats = statBalancer.enemyFromPlayer(playerStats);

        String enemyName = payload.enemy().name();
        int enemyLevel = profile.level();
        long generatedEnemyId = genRepo.insert(conn, matchId, promptUserText, enemyName, enemyLevel, enemyJson);

        BattleResultDTO battleResult = battleEngine.fight(sc, playerStats, enemyStats, payload);

        matchRepo.finishMatch(conn, matchId, battleResult.result(), battleResult.turns(), battleResult.xpGained());

        battleRepo.insertAI(conn, matchId, generatedEnemyId,
            battleResult.result(),
            battleResult.turns(),
            battleResult.damageDealt(),
            battleResult.damageTaken());

        conn.commit();
        String[] saveBattle = { "Partida guardada (match + generated_enemy + battle). matchId=" + matchId + "\n\n" };
        TypingText.printText(saveBattle);

      } catch (Exception e) {
        conn.rollback();

        throw e;
      } finally {
        conn.setAutoCommit(old);
      }
    }
  }
}
