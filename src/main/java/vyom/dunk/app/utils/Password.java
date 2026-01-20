package vyom.dunk.app.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
  public static String hashPassword(String pass) {
    String s = BCrypt.gensalt();
    return BCrypt.hashpw(pass, s);
  }

  public static boolean checkPassword(String pass, String passHash) {
    return BCrypt.checkpw(pass, passHash);
  }
}
