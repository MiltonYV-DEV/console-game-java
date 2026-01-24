package vyom.dunk.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

  private final String url;
  private final String user;
  private final String pass;

  public Database(String url, String user, String pass) {
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  public Connection getConnection() throws SQLException {
    Connection conn = DriverManager.getConnection(url, user, pass);
    return conn;
  }

  // public String holaMundp(String palabra) throw IllegalArgumentException {
  //
  // if (palabra != "hola") {
  // // Crea un error
  // throw new IllegalArgumentException("El string recibido no es hola");
  // }
  // }
}
