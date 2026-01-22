package vyom.dunk.app.resources;

import java.util.List;

public record EnemyPayload(
    Enemy enemy,
    List<Attack> enemy_attacks,
    List<Attack> player_attacks) {

  public record Enemy(String name, String alias, String description, String opening_dialogue) {
  }

  public record Attack(String name, int damage, String description) {
  }
}
