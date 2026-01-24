package vyom.dunk.app.resources;

public record RankingRowDTO(long userId, String username, int wins, int loses, int totalMatches) {
}
