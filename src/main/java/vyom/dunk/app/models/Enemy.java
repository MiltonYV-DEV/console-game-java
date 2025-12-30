package vyom.dunk.app.models;

import java.util.Random;

public class Enemy extends BaseCharacter {
  private final String description;
  private final String nameAttack;
  private final String[] dialogsAttacks;
  private final int potions = 2;

  public Enemy(String name, int hp, String description, String nameAttack, String[] dialogsAttacks) {
    super(name, hp);
    this.description = description;
    this.nameAttack = nameAttack;
    this.dialogsAttacks = dialogsAttacks;
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
    int old = this.getHp();
    int newHp = Math.max(0, this.getHp() - dmg);
    this.setHp(newHp);
    return old - this.getHp();
  }

  public String randomDialogAttack(String[] dialogsAttack) {
    Random random = new Random();
    return dialogsAttack[random.nextInt(dialogsAttack.length)];
  }

  public boolean isAlive() {
    return this.getHp() > 0;
  }

}
