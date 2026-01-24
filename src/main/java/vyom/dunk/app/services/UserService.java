package vyom.dunk.app.services;

import java.sql.Connection;
import java.sql.SQLException;

import vyom.dunk.app.config.Database;
import vyom.dunk.app.repositories.CharacterRepository;
import vyom.dunk.app.repositories.UserRepository;
import vyom.dunk.app.resources.LoginDTO;
import vyom.dunk.app.resources.LoginResponseDTO;
import vyom.dunk.app.resources.ProfileDTO;
import vyom.dunk.app.resources.RegisterDTO;
import vyom.dunk.app.resources.RegisterResponseDTO;
import vyom.dunk.app.resources.UserAuthData;
import vyom.dunk.app.utils.Password;

public class UserService {
  private final Database db;
  private final UserRepository userRepo;
  private final CharacterRepository charRepo;

  public UserService(Database db, UserRepository userRepo, CharacterRepository charRepo) {
    this.db = db;
    this.userRepo = userRepo;
    this.charRepo = charRepo;
  }

  public RegisterResponseDTO register(RegisterDTO req) throws SQLException {
    if (req.username() == null || req.username().isBlank())
      throw new IllegalArgumentException("Username requerido");

    if (req.password() == null || req.password().isBlank())
      throw new IllegalArgumentException("Password requerido");

    String username = req.username().trim();
    String characterName = (req.characterName() == null || req.characterName().isBlank() ? username
        : req.characterName().trim());

    try (Connection conn = db.getConnection()) {
      boolean oldAuto = conn.getAutoCommit();
      conn.setAutoCommit(false);

      try {
        if (userRepo.existsByUsername(conn, username))
          throw new IllegalArgumentException("Este username ya existe");

        String hash = Password.hashPassword(req.password());
        long userId = userRepo.insert(conn, username, hash);
        long characterId = charRepo.insertDefault(conn, userId, characterName);

        conn.commit();

        return new RegisterResponseDTO(userId, characterId, username);

      } catch (Exception e) {
        conn.rollback();

        if (e instanceof IllegalArgumentException)
          throw (IllegalArgumentException) e;

        if (e instanceof SQLException)
          throw (SQLException) e;

        throw new SQLException("Error en el registro", e);

      } finally {
        conn.setAutoCommit(oldAuto);
      }
    }
  }

  public LoginResponseDTO login(LoginDTO req) throws SQLException {
    if (req.username() == null || req.username().isBlank())
      throw new IllegalArgumentException("Username es requerido");

    if (req.password() == null || req.password().isBlank())
      throw new IllegalArgumentException("Password es requerido");

    String username = req.username();

    try (Connection conn = db.getConnection()) {
      UserAuthData user = userRepo.selectAuthByUsername(conn, username) // id, username, passwordHash
          .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

      if (!Password.checkPassword(req.password(), user.passwordHash()))
        throw new IllegalArgumentException("Credenciales inválidas");

      return new LoginResponseDTO(user.id(), user.username());
    }
  }

  public ProfileDTO getProfile(long userId) throws SQLException {
    try (Connection conn = db.getConnection()) {
      return charRepo.selectProfileById(conn, userId)
          .orElseThrow(() -> new IllegalArgumentException("No se encontró perfin para este id"));
    }
  }
}
