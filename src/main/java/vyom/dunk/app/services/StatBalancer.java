package vyom.dunk.app.services;

import java.util.concurrent.ThreadLocalRandom;
import vyom.dunk.app.resources.CombatStats;

public class StatBalancer {
  public CombatStats enemyFromPlayer(CombatStats player) {
    var r = ThreadLocalRandom.current();

    int hp = player.hp() + r.nextInt(10, 41);
    int atk = Math.max(5, player.attack() - 2 + r.nextInt(0, 7));
    int def = Math.max(0, player.defense() - 1 + r.nextInt(0, 5));

    return new CombatStats(hp, atk, def);
  }
}
