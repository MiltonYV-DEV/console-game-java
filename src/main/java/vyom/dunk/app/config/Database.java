package vyom.dunk.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private static final String URL = "jdbc:mysql://localhost:3000/poo_pf";
  private static final String USER = "root";
  private static final String PASS = "12345";

  public static Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(URL, USER, PASS);
    return con;
  }
}
