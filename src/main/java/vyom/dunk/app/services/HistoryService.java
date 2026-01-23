package vyom.dunk.app.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import vyom.dunk.app.config.Database;
import vyom.dunk.app.repositories.HistoryRepository;
import vyom.dunk.app.resources.MatchHistoryRowDTO;

public class HistoryService {
  private final Database db;
  private final HistoryRepository historyRepo;

  public HistoryService(Database db, HistoryRepository historyRepo) {
    this.db = db;
    this.historyRepo = historyRepo;
  }

  public List<MatchHistoryRowDTO> getLastMatches(long userId, int limit) throws SQLException {
    if (limit <= 0 || limit > 50)
      limit = 10;

    try (Connection conn = db.getConnection()) {
      return historyRepo.lastMatchesByUser(conn, userId, limit);
    }
  }

}
