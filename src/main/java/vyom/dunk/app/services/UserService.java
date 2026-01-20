package vyom.dunk.app.services;

import vyom.dunk.app.repositories.UserRepository;
import vyom.dunk.app.resources.UserAuthData;
import vyom.dunk.app.resources.UserCreateRequest;
import vyom.dunk.app.utils.Password;

public class UserService {
  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public long createUser(UserCreateRequest req) {
    if (req.username() == null || req.username().isBlank()) {
      throw new IllegalArgumentException("Username requerido");
    }

    if (req.pass() == null || req.pass().isBlank()) {
      throw new IllegalArgumentException("Password vacio");
    }

    if (repo.existsByUsername(req.username())) {
      throw new IllegalArgumentException("Este username ya esta registrado");
    }

    String hash = Password.hashPassword(req.pass());
    return repo.insert(req.username().trim(), hash);
  }

  public String loginUser(String username, String password) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username requerido");
    }

    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password requerido");
    }

    UserAuthData user = repo.selectUserByUsername(username.trim())
        .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

    if (!Password.checkPassword(password, user.passwordHash())) {
      throw new IllegalArgumentException("Credenciales inválidas");
    }

    return user.username();
  }

}
