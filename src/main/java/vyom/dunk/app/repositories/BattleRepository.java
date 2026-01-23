package vyom.dunk.app.repositories;

import java.sql.*;

public class BattleRepository {
  public long insertAI(Connection conn,
      long matchId,
      long generatedEnemyId,
      String result,
      int turns,
      int damageDealts,
      int damageTaken) throws SQLException {

    String sql = """
          INSERT INTO battles (match_id, enemy_source, generated_enemy_id, result, turns, damage_dealt, damage_taken)
          VALUES (?, 'AI', ?, ?, ?, ?, ?)
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, matchId);
      ps.setLong(2, generatedEnemyId);
      ps.setString(3, result);
      ps.setInt(4, turns);
      ps.setInt(5, damageDealts);
      ps.setInt(6, damageTaken);

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (!rs.next())
          throw new SQLException("No se pudo obtener battle_id");

        return rs.getLong(1);
      }
    }
  }
}
