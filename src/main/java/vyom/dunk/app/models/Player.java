package vyom.dunk.app.models;

public class Player extends BaseCharacter {

  public Player(String name, int hp) {
    super(name, hp);
  }

  // Turno
  public void startTurn() {

  }

  // efectos
  public int takeDamage(int incomingDamage) {
    if (incomingDamage < 0)
      incomingDamage = 0;

    int reduced = Math.max(0, incomingDamage);
    int oldHp = this.getHp();
    int newHp = Math.max(0, this.getHp() - reduced);

    this.setHp(newHp);
    return oldHp - this.getHp();
  }
}
