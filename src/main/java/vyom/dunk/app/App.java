package vyom.dunk.app;

import java.io.IOException;

import vyom.dunk.app.config.Database;
import vyom.dunk.app.repositories.CharacterRepository;
import vyom.dunk.app.repositories.UserRepository;
import vyom.dunk.app.services.UserService;
import vyom.dunk.app.ui.Ui;

public class App {
  public static void main(String[] args) throws IOException {

    Database db = new Database("jdbc:mysql://localhost:3306/poo_pf", "root", "12345");

    UserRepository userRepo = new UserRepository();
    CharacterRepository charRepo = new CharacterRepository();
    UserService userService = new UserService(db, userRepo, charRepo);

    Ui ui = new Ui(userService);

    ui.home();
  }
}
