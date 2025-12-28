package vyom.dunk.app.models;

import java.util.Random;

public class Enemy {
  private final String name;
  private final int maxHp;

  private final String description;
  private final String nameAttack;
  private final String[] dialogsAttacks;

  private int hp;

  public Enemy(String name, int maxHp, String description, String nameAttack, String[] dialogsAttacks) {
    this.name = name.trim();
    this.maxHp = 100;
    this.hp = 100;
    this.description = description;
    this.nameAttack = nameAttack;
    this.dialogsAttacks = dialogsAttacks;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getNameAttack() {
    return nameAttack;
  }

  public int takeDamage(int dmg) {
    if (dmg < 0)
      dmg = 0;
    int old = hp;
    hp = Math.max(0, hp - dmg);
    return old - hp;
  }

  public String randomDialogAttack(String[] dialogsAttack) {
    Random random = new Random();
    return dialogsAttack[random.nextInt(dialogsAttack.length)];
  }

}
