package vyom.dunk.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vyom.dunk.app.resources.RankingRowDTO;

public class RankingRepository {

  public List<RankingRowDTO> topUsersByWins(Connection conn, int limit) throws SQLException {
    String sql = """
        SELECT
          u.id,
          u.username,
          SUM(CASE WHEN m.result = 'WIN' THEN 1 ELSE 0 END) AS wins,
          SUM(CASE WHEN m.result = 'LOSE' THEN 1 ELSE 0 END) as loses,
          COUNT(m.id) AS total_matches

        FROM users u
        LEFT JOIN matches m ON m.user_id = u.id
        GROUP BY u.id, u.username
        ORDER BY wins DESC, total_matches DESC
        LIMIT ?
          """;

    List<RankingRowDTO> out = new ArrayList<>();

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, limit);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(new RankingRowDTO(
              rs.getLong("id"),
              rs.getString("username"),
              rs.getInt("wins"),
              rs.getInt("loses"),
              rs.getInt("total_matches")));
        }
      }
    }

    return out;
  }
}
