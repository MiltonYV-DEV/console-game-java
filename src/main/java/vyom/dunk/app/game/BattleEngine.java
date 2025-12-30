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
    System.out.println(player.getName() + " vs " + enemy.getName() + "\n");

    while (player.isAlive() && enemy.isAlive()) {
      printStatus(player, enemy);
      System.out.println("Tu turno: ");
      System.out.println("1) Atacar");
      System.out.println("2) Curar (+10)");
      System.out.print("> ");

      String opt = sc.nextLine().trim();

      switch (opt) {
        case "1" -> {
          int dmg = rollDamage(8, 12);
          enemy.takeDamage(dmg);
          System.out.println("Te curaste e hiciste " + dmg + " de dano.");
        }

        case "2" -> {
          int healted = player.heal(10);
          System.out.println("Te curaste " + healted + " HP.");
        }

      }
    }
  }

  private int rollDamage(int min, int max) {
    return rng.nextInt((max - min) + 1) + min;
  }

  private void printStatus(Player p, Enemy e) {
    System.out.println("----- Estado -----");
    System.out.println(p.getName() + ": " + p.getHp() + "/" + p.getMaxHp());
    System.out.println(e.getName() + ": " + e.getHp() + "/" + e.getMaxHp());
    System.out.println("----------------");
  }
}
