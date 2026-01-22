package vyom.dunk.app.resources;

public record MatchFinishDTO(
    String result,
    int totalTurns,
    int xpGained) {
}
