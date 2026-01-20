package vyom.dunk.app.services;

import vyom.dunk.app.repositories.UserRepository;
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
}
