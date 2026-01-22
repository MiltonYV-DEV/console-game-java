package vyom.dunk.app.resources;

public record BattleResultDTO(
    String result,
    int turns,
    int damageDealt,
    int damageTaken,
    int xpGained) {
}
