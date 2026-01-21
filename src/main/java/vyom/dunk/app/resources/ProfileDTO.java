package vyom.dunk.app.resources;

public record ProfileDTO(
    long userId,
    String username,
    long characterId,
    String characterName,
    int level,
    int hp,
    int attack,
    int defense) {
}
