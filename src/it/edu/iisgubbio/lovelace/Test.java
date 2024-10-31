package it.edu.iisgubbio.lovelace;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * La mia prima classe
 */
public class Test extends Application {

  Button pSaluto = new Button(); 

  public void start(Stage finestra) {
    pSaluto.setText("Saluta!");
    pSaluto.setOnAction(e -> esegui());

    BorderPane principale = new BorderPane();
    principale.setCenter(pSaluto);

    Scene scena = new Scene(principale, 300, 250);

    finestra.setTitle("Hello World!");
    finestra.setScene(scena);
    finestra.show();
  }

  private void esegui(){
    pSaluto.setText("Ciao Mondo!");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
