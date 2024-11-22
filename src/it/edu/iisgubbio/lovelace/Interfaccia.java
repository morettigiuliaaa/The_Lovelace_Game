package it.edu.iisgubbio.lovelace;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Interfaccia extends Application{
	Pane quadro =new Pane();
	Button bStart=new Button("Start");
	Button bSettings=new Button("Settings");
	Label eTitolo=new Label("The Lovelace Game");
	final int ALTEZZA_QUADRO=700;
	final int LARGHEZZA_QUADRO=800;

	public void start(Stage finestra) {
		quadro.getChildren().addAll(eTitolo, bStart, bSettings );
		eTitolo.setLayoutX(LARGHEZZA_QUADRO/2-50);
		eTitolo.setLayoutY(ALTEZZA_QUADRO-600);
		bStart.setLayoutY((ALTEZZA_QUADRO/2)+25);
		bStart.setLayoutX(LARGHEZZA_QUADRO/2-50);
		Scene scena = new Scene(quadro,LARGHEZZA_QUADRO,ALTEZZA_QUADRO);
		finestra.setScene(scena);
		finestra.show();
	}
	public static void main(String[] args) {
		launch(args);	

	}
}
