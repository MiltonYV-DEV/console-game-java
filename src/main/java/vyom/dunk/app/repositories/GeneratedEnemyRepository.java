package vyom.dunk.app.repositories;

import java.sql.*;

public class GeneratedEnemyRepository {
  public long insert(Connection conn,
      long matchId,
      String promptUserText,
      String enemyName,
      int enemyLevel,
      String statsJson) throws SQLException {
    String sql = """
          INSERT INTO generated_enemies (match_id, prompt_user_text, enemy_name, enemy_level, stats_json)
          VALUES (?, ?, ?, ?, CAST(? AS JSON))
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, matchId);
      ps.setString(2, promptUserText);
      ps.setString(3, enemyName);
      ps.setInt(4, 1);
      ps.setString(5, statsJson);

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (!rs.next())
          throw new SQLException("No se pudo obtener generated_enemy_id");

        return rs.getLong(1);
      }
    }

  }
}
