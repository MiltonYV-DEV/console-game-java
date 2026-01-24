package vyom.dunk.app.repositories;

import java.io.OptionalDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // resultSet es lo que te va devolver la base
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import vyom.dunk.app.resources.UserAuthData;

public class UserRepository {

  public boolean existsByUsername(Connection conn, String username) throws SQLException {
    String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);

      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }

  public long insert(Connection conn, String username, String passwordHash) throws SQLException {
    String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, username);
      ps.setString(2, passwordHash);
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) { // 1, 2, 3, 4
        if (!rs.next()) // si la primary key no existe
          throw new SQLException("No se pudo obtener user_id");

        return rs.getLong(1);
      }
    }
  }

  public Optional<UserAuthData> selectAuthByUsername(Connection conn, String username) throws SQLException {
    String sql = "SELECT id, username, password_hash FROM users WHERE username = ? LIMIT 1";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);

      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        return Optional.of(new UserAuthData(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password_hash")));
      }
    }
  }
}
