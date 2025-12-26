package vyom.dunk.app.game;

import java.util.Random;
import java.util.Scanner;

import vyom.dunk.app.models.Enemy;
import vyom.dunk.app.models.Player;
import vyom.dunk.app.ui.Ui;

public class BattleEngine {
  private final Random rng = new Random();

  public void start(Player player, Enemy enemy, Scanner sc) {

    System.out.println("\n=== COMBATE ===");
    System.out.println(player.getName() + " vs " + enemy.getNane() + "\n");
  }
}
