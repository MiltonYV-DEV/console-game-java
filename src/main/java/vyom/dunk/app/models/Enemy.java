package vyom.dunk.app.models;

public class Enemy {
  private final String name;
  private final int maxHp;

  private final String description;
  private final String nameAttack;
  private final String[] dialogsAttacks;

  private int hp;

  public Enemy(String name, int maxHp, String description, String nameAttack, String[] dialogsAttacks) {
    this.name = name.trim();
    this.maxHp = maxHp;
    this.hp = maxHp;
    this.description = description;
    this.nameAttack = nameAttack;
    this.dialogsAttacks = dialogsAttacks;
  }

  public String getNane() {
    return name;
  }

  public int takeDamage(int dmg) {
    if (dmg < 0)
      dmg = 0;
    int old = hp;
    hp = Math.max(0, hp - dmg);
    return old - hp;
  }
}
