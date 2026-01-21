package vyom.dunk.app.repositories;

import java.sql.*;

public class CharacterRepository {

  public long insertDefault(Connection conn, long userId, String characterName) throws SQLException {
    String sql = """
          INSERT INTO characters (user_id, name, level, hp, attack, defense)
          VALUES (?, ?, 1, 100, 20, 5)
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, userId);
      ps.setString(2, characterName);
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (!rs.next())
          throw new SQLException("No se pudo obtener character_id");

        return rs.getLong(1);
      }
    }
  }
}
