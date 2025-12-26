package vyom.dunk.app.models;

public class Player {
  private final String name;
  private final Stats stats;

  private int hp;
  private boolean defending;

  public Player(String name, Stats stats) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("Nombre invalido");

    if (stats == null)
      throw new IllegalArgumentException("Stats no puede ser null");

    this.name = name;
    this.stats = stats;
    this.hp = stats.getMaxHp();
    this.defending = false;
  }

  // Getters
  public String getName() {
    return name;
  }

  public Stats getStats() {
    return stats;
  }

  public int getHp() {
    return hp;
  }

  public int getMaxHp() {
    return stats.getMaxHp();
  }

  public boolean isAlive() {
    return hp > 0;
  }

  public boolean isDefending() {
    return defending;
  }

  // Turno
  public void startTurn() {

  }

  public void endTurn() {
    defending = false;
  }

  // acciones/estado
  public void defend() {
    defending = true;
  }

  public int getAttackPower() {
    return stats.getAttack();
  }

  public int getDefensePower() {
    return stats.getDefense();
  }

  // efectos
  public int takeDamage(int incomingDamage) {
    if (incomingDamage < 0)
      incomingDamage = 0;

    int reduced = Math.max(0, incomingDamage - getDefensePower());
    int oldHp = hp;
    hp = Math.max(0, hp - reduced);

    return oldHp - hp;
  }

  public int heal(int amount) {
    if (amount <= 0)
      return 0;

    int oldHp = hp;
    hp = Math.min(stats.getMaxHp(), hp + amount);

    return hp - oldHp;
  }
}
