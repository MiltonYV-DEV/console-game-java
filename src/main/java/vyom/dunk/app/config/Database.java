package vyom.dunk.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  // private final String URL = "jdbc:mysql://localhost:3000/poo_pf";
  // private final String USER = "root";
  // private final String PASS = "12345";

  private final String url;
  private final String user;
  private final String pass;

  public Database(String url, String user, String pass) {
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  public Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(url, user, pass);
    return con;
  }
}
