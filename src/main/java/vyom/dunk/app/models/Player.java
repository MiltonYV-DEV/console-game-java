package vyom.dunk.app.models;

public class Player {
  private final String name;
  private int maxHP;
  private int hp;
  private int potions;
  private int attack;

  public Player(String name) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("Nombre invalido");

    this.name = name;
    this.hp = 100;
    this.maxHP = 100;
    this.potions = 4;
    this.attack = 10;
  }

  // Getters
  public String getName() {
    return name;
  }

  public int getHp() {
    return hp;
  }

  public int getMaxHp() {
    return this.maxHP;
  }

  public boolean isAlive() {
    return hp > 0;
  }

  // Turno
  public void startTurn() {

  }

  public int getAttack() {
    return this.attack;
  }

  // efectos
  public int takeDamage(int incomingDamage) {
    if (incomingDamage < 0)
      incomingDamage = 0;

    int reduced = Math.max(0, incomingDamage);
    int oldHp = hp;
    hp = Math.max(0, hp - reduced);

    return oldHp - hp;
  }
}
