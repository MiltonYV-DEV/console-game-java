package vyom.dunk.app.repositories;

import java.sql.*;

public class MatchRepository {
  public long createMatch(Connection conn, long userId) throws SQLException {
    String sql = "INSERT INTO matches (user_id) VALUES (?)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, userId);
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (!rs.next())
          throw new SQLException("No se puede obtener match_id");
        return rs.getLong(1);
      }
    }
  }

  public void finishMatch(Connection conn,
      long matchId,
      String result,
      int totalTurns,
      int xpGained) throws SQLException {
    String sql = """
          UPDATE matches
          SET ended_at = CURRENT_TIMESTAMP,
            result = ?,
            total_turns = ?,
            xp_gained = ?
          WHERE id = ?
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, result);
      ps.setInt(2, totalTurns);
      ps.setInt(3, xpGained);
      ps.setLong(4, matchId);

      int updated = ps.executeUpdate();
      if (updated != 1)
        throw new SQLException("No se pudo finalizar match. match_id=" + matchId);
    }
  }
}
