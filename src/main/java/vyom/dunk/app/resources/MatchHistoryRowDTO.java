package vyom.dunk.app.resources;

import java.sql.Timestamp;

public record MatchHistoryRowDTO(
    long matchId,
    Timestamp startedAt,
    Timestamp endedAt,
    String result,
    int totalTurns,
    int xpGained,
    String enemyName,
    int damageDealt,
    int damageTaken) {
}
