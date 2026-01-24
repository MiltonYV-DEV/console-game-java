package vyom.dunk.app.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import vyom.dunk.app.config.Database;
import vyom.dunk.app.repositories.RankingRepository;
import vyom.dunk.app.resources.RankingRowDTO;

public class RankingService {
  private final Database db;
  private final RankingRepository rankingRepo;

  public RankingService(Database db, RankingRepository rankingRepo) {
    this.db = db;
    this.rankingRepo = rankingRepo;
  }

  public List<RankingRowDTO> topWins(int limit) throws SQLException {
    if (limit <= 0 || limit > 50)
      limit = 10;
    try (Connection conn = db.getConnection()) {
      return rankingRepo.topUsersByWins(conn, limit);
    }
  }
}
