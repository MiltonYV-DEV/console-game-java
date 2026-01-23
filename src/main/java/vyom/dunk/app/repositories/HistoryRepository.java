package vyom.dunk.app.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import vyom.dunk.app.resources.MatchHistoryRowDTO;

public class HistoryRepository {
  public List<MatchHistoryRowDTO> lastMatchesByUser(Connection conn, long userId, int limit) throws SQLException {
    String sql = """
          SELECT m.id AS match_id, m.started_at, m.ended_at, m.result, m.total_turns, m.xp_gained,
                  ge.enemy_name,
                  COALESCE(b.damage_dealt, 0) AS damage_dealt,
                  COALESCE(b.damage_taken, 0) AS damage_taken
          FROM matches m
          LEFT JOIN battles b ON b.match_id = m.id
          LEFT JOIN generated_enemies ge ON ge.id = b.generated_enemy_id
          WHERE m.user_id = ?
          ORDER BY m.started_at DESC
          LIMIT ?
        """;

    List<MatchHistoryRowDTO> out = new ArrayList<>();

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, userId);
      ps.setInt(2, limit);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(new MatchHistoryRowDTO(
              rs.getLong("match_id"),
              rs.getTimestamp("started_at"),
              rs.getTimestamp("ended_at"),
              rs.getString("result"),
              rs.getInt("total_turns"),
              rs.getInt("xp_gained"),
              rs.getString("enemy_name"),
              rs.getInt("damage_dealt"),
              rs.getInt("damage_taken")));
        }
      }
    }
    return out;
  }
}
