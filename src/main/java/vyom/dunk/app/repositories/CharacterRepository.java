package vyom.dunk.app.repositories;

import java.sql.*;
import java.util.Optional;

import vyom.dunk.app.resources.ProfileDTO;

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

  public Optional<ProfileDTO> selectProfileById(Connection conn, long userId) throws SQLException {
    String sql = """
          SELECT u.id as user_id, u.username,
                  c.id as character_id, c.name as character_name,
                  c.level, c.hp, c.attack, c.defense
          FROM users u
          JOIN characters c ON c.user_id = u.id
          WHERE u.id = ?
          LIMIT 1
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, userId);

      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(new ProfileDTO(
            rs.getLong("user_id"),
            rs.getString("username"),
            rs.getLong("character_id"),
            rs.getString("character_name"),
            rs.getInt("level"),
            rs.getInt("hp"),
            rs.getInt("attack"),
            rs.getInt("defense")));
      }
    }
  }
}
