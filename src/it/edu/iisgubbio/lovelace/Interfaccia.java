package it.edu.iisgubbio.lovelace;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import it.edu.iisgubbio.lovelace.dynamicEffects.*;

public class Interfaccia extends Application{
	Pane areaGioco = new Pane();
	Label eTitolo = new Label("The Lovelace Game");
	Label eSottoTitolo = new Label("Aiuta Ada a costruire il suo algoritmo!");
	Button bInizio = new Button("Inizio");
	Button bImpostazioni = new Button("Impostazioni");
	Button bEsci = new Button("Esci");
	Button bTornaAlMenu = new Button("🏠");
	Group home = new Group();
	
	
	final int LARGHEZZA_AREA_GIOCO = 900;
	final int ALTEZZA_AREA_GIOCO = 700;



	public void start(Stage finestra) throws Exception {
		
		home.getChildren().add(eTitolo);
		home.getChildren().add(eSottoTitolo);
		home.getChildren().add(bInizio);
		home.getChildren().add(bImpostazioni);
		home.getChildren().add(bEsci);
		
		areaGioco.getChildren().add(home);
		
		(new FadeIn(home, 2000, 30)).start();
		
		eTitolo.setLayoutX(340);
		eTitolo.setLayoutY(100);
		
		eSottoTitolo.setLayoutX(342);
		eSottoTitolo.setLayoutY(150);
		
		bInizio.setLayoutX(342);
		bInizio.setLayoutY(250);
		
		bImpostazioni.setLayoutX(342);
		bImpostazioni.setLayoutY(310);
		
		bEsci.setLayoutX(342);
		bEsci.setLayoutY(373);
		
		bInizio.setPrefWidth(200);
		bImpostazioni.setPrefWidth(200);
		bEsci.setPrefWidth(200);
		
		bImpostazioni.setOnAction(e-> impostazioni());
		bEsci.setOnAction(e-> esci());
		
		Image icon = new Image(getClass().getResourceAsStream("immagini/IMG_2263.jpeg"));
	    finestra.getIcons().add(icon);
		
		
		eTitolo.setId("titolo");
		areaGioco.setId("paneSfondo");
		

		Scene scena = new Scene(areaGioco,LARGHEZZA_AREA_GIOCO,ALTEZZA_AREA_GIOCO);
		scena.getStylesheets().add("it/edu/iisgubbio/lovelace/foglio.css");
		

		finestra.setTitle("The Lovalace Game!");
		finestra.setResizable(false);
		finestra.setScene(scena);
		finestra.show();
	}
	
	public void impostazioni() {
		Group effetto = new Group();
		Label lingua = new Label("Lingua:");
		lingua.setId("testo");
		ToggleSwitch button = new ToggleSwitch();
		effetto.getChildren().add(bTornaAlMenu);
		effetto.getChildren().add(button);
		(new FadeIn(effetto, 2000)).start();
		areaGioco.getChildren().clear();
		areaGioco.setId("paneSfondo");
		areaGioco.getChildren().add(effetto);
		areaGioco.getChildren().add(lingua);
		button.setLayoutX(381);
		button.setLayoutY(373);
		lingua.setLayoutX(390);
		lingua.setLayoutY(355);
		bTornaAlMenu.setLayoutX(10);
		bTornaAlMenu.setLayoutY(7);
		
	}
	
	public void esci() {
		Platform.exit();
	}

	public static void main(String args[]){
		launch();
	}
}