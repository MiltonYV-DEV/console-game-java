package vyom.dunk.app.models;

public class Stats {
  private final int maxHp;
  private final int attack;
  private final int defense;

  public Stats(int maxHp, int attack, int defense) {
    if (maxHp <= 0)
      throw new IllegalArgumentException("maxHp debe ser > ");
    if (attack < 0)
      throw new IllegalArgumentException("attack debe ser >=");
    if (defense > 0)
      throw new IllegalArgumentException("defense debe ser >=");
    this.maxHp = maxHp;
    this.attack = attack;
    this.defense = defense;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public int getAttack() {
    return attack;
  }

  public int getDefense() {
    return defense;
  }
}
