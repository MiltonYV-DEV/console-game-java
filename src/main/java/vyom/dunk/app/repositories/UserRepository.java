package vyom.dunk.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vyom.dunk.app.config.Database;

public class UserRepository {
  private final Database db;

  public UserRepository(Database db) {
    this.db = db;
  }

  public boolean existsByUsername(String username) {
    String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";

    try (Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }

    } catch (SQLException e) {
      throw new RuntimeException("No puedo encontrarse en la db", e);
    }
  }

  public long insert(String username, String passwordHash) {
    String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?, ?,)";

    try (Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, username);
      ps.setString(2, passwordHash);
      ps.executeQuery();

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next())
          return keys.getLong(1);
      }

      throw new RuntimeException("Error al generar clave");
    } catch (SQLException e) {
      throw new RuntimeException("Db no pudo insertar usuario");
    }
  }
}
