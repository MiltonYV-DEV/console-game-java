package vyom.dunk.app.models;

public class BaseCharacter {
  private final String name;
  private int hp;
  private int maxHp;

  public BaseCharacter(String name, int hp) {
    this.name = name.trim();
    this.hp = hp;
    this.maxHp = hp;
  }

  // getters
  public String getName() {
    return this.name;
  }

  public int getHp() {
    return this.hp;
  }

  public int getMaxHp() {
    return this.maxHp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public boolean isAlive() {
    return hp > 0;
  }

  // acciones
  public int takeDamage(int dmg) {
    if (dmg < 0)
      dmg = 0;

    int old = this.hp;
    int newHp = Math.max(0, this.hp - dmg);
    this.hp = newHp;

    return old - this.hp;
  }

  public int heal(int addHp) {
    this.hp += addHp;
    return addHp;
  }

}
