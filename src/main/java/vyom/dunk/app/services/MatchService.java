package vyom.dunk.app.services;

import java.sql.Connection;
import java.sql.SQLException;

import vyom.dunk.app.repositories.MatchRepository;
import vyom.dunk.app.resources.MatchFinishDTO;
import vyom.dunk.app.resources.MatchStartResponseDTO;
import vyom.dunk.app.config.Database;

public class MatchService {
  private final Database db;
  private final MatchRepository matchRepo;

  public MatchService(Database db, MatchRepository matchRepo) {
    this.db = db;
    this.matchRepo = matchRepo;
  }

  public MatchStartResponseDTO startMatch(long userId) throws SQLException {
    try (Connection conn = db.getConnection()) {
      long matchId = matchRepo.createMatch(conn, userId);
      return new MatchStartResponseDTO(matchId);
    }
  }

  public void finishMatch(long matchId, MatchFinishDTO dto) throws SQLException {
    try (Connection conn = db.getConnection()) {
      matchRepo.finishMatch(conn, matchId, dto.result(), dto.totalTurns(), dto.xpGained());
    }
  }

  public void validateFinish(MatchFinishDTO dto) {
    if (dto.result() == null || dto.result().isBlank())
      throw new IllegalArgumentException("Result requerido");

    String r = dto.result().strip().toUpperCase();

    boolean ok = r.equals("WIN") || r.equals("LOSE") || r.equals("ABANDONED");

    if (!ok)
      throw new IllegalArgumentException("Result invalido. Usa WIN, LOSE o ABANDONED");

    if (dto.totalTurns() < 0)
      throw new IllegalArgumentException("totalTurns inválido");

    if (dto.xpGained() < 0)
      throw new IllegalArgumentException("xpGained inválido");

  }
}
