package vyom.dunk.app.game;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import vyom.dunk.app.resources.BattleResultDTO;
import vyom.dunk.app.resources.CombatStats;
import vyom.dunk.app.resources.EnemyPayload;
import vyom.dunk.app.ui.Ui;
import vyom.dunk.app.utils.TypingText;

public class BattleEngine {

  public BattleResultDTO fight(Scanner sc, CombatStats playerStats, CombatStats enemyStats, EnemyPayload payload) {
    int playerHp = playerStats.hp();
    int enemyHp = enemyStats.hp();

    int turns = 0;
    int damageDealt = 0;
    int damageTaken = 0;

    String[] titleFight = {
        "BATALLA\n",
        payload.enemy().name() + " - " + payload.enemy().alias() + "\n\n",
        "Narrador: " + payload.enemy().description() + "\n\n",
        payload.enemy().name() + ": " + payload.enemy().opening_dialogue() + "\n\n",
        "Stats Enemigo: HP=" + enemyStats.hp() + " ATK=" + enemyStats.attack() + " DEF=" + enemyStats.defense() + "\n",
        "Tus Stats:   HP=" + playerStats.hp() + " ATK=" + playerStats.attack() + " DEF=" + playerStats.defense()
            + "\n\n"
    };
    Ui.readContinue();
    Ui.clearScreen();
    TypingText.printText(titleFight);
    Ui.readContinue();
    Ui.clearScreen();

    while (playerHp > 0 && enemyHp > 0) {
      turns++;
      Ui.clearScreen();
      // jugador
      String[] initialTurnelements = { "Turno " + turns + "\n\n",
          "Tu HP: " + playerHp + " | HP Enemigo: " + enemyHp + "\n\n" };

      TypingText.printText(initialTurnelements);
      printAttacks(payload.player_attacks());

      int choice = readAttackChoice(sc, payload.player_attacks().size());
      EnemyPayload.Attack atk = payload.player_attacks().get(choice - 1);

      int dealt = calcDamage(atk.damage(), playerStats.attack(), enemyStats.defense());
      enemyHp -= dealt;
      damageDealt += dealt;

      String[] playerChoice = { "Usaste: " + atk.name() + " -> daño " + dealt + "\n",
          atk.description() + "\n\n" };

      TypingText.printText(playerChoice);

      if (enemyHp <= 0)
        break;

      // enemigo
      EnemyPayload.Attack enemyAtk = randomAttack(payload.enemy_attacks());
      int taken = calcDamage(enemyAtk.damage(), enemyStats.attack(), playerStats.defense());
      playerHp -= taken;
      damageTaken += taken;

      String[] enemyChoice = {
          "El enemigo usa: " + enemyAtk.name() + " -> daño " + taken + "\n",
          enemyAtk.description() + "\n" };

      TypingText.printText(enemyChoice);

      Ui.readContinue();
    }

    String result = (playerHp > 0) ? "WIN" : "LOSE";

    int xp = result.equals("WIN") ? 20 : 5;

    String[] resultFight = {
        "\nRESULTADO" + "\n",
        result + " | Turnos: " + turns + " | Daño hecho: " + damageDealt + " | Daño recibido: " + damageTaken + "\n\n"
    };

    TypingText.printText(resultFight);

    return new BattleResultDTO(result, turns, damageDealt, damageTaken, xp);
  }

  private void printAttacks(List<EnemyPayload.Attack> atks) {
    for (int i = 0; i < atks.size(); i++) {
      var a = atks.get(i);
      String[] printAttacksElements = { "" + (i + 1) + ")" + a.name() + " (base " + a.damage() + ")" + "\n" };

      TypingText.printText(printAttacksElements);
    }

    String[] atackOpt = { "\nElige ataque: " };
    TypingText.printText(atackOpt);

  }

  private int readAttackChoice(Scanner sc, int max) {
    while (true) {
      String l = sc.nextLine().trim();
      try {
        int n = Integer.parseInt(l);
        if (n >= 1 && n <= max)
          return n;

      } catch (Exception ignored) {
        String[] errorReadAttackChoise = { "Opcion invalida, elige 1-" + max + ": " };
        TypingText.printText(errorReadAttackChoise);
      }
    }
  }

  private EnemyPayload.Attack randomAttack(List<EnemyPayload.Attack> atks) {
    int random = ThreadLocalRandom.current().nextInt(atks.size());
    return atks.get(random);
  }

  private int calcDamage(int base, int atk, int def) {
    int d = base + (atk - def);
    return Math.max(1, d);
  }
}
