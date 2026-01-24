package vyom.dunk.app.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
  public static String hashPassword(String password) {
    String s = BCrypt.gensalt();
    return BCrypt.hashpw(password, s);
  }

  public static boolean checkPassword(String password, String passwordHash) {
    return BCrypt.checkpw(password, passwordHash);
  }
}
