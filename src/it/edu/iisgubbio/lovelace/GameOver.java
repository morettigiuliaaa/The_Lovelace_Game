package it.edu.iisgubbio.lovelace;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import it.edu.iisgubbio.lovelace.*; 
import javafx.scene.Group;

public class GameOver {
	RaccoltaTesti testi;
	Group group;
	Pane areaGioco;
    public GameOver(Pane areaGioco, RaccoltaTesti testoDialogo, Group gruppo) {
        // Imposta lo sfondo nero dell'AreaGioco
        areaGioco.getChildren().clear();
        testi=testoDialogo;
        group=gruppo;
        this.areaGioco=areaGioco;
        //areaGioco.setStyle("-fx-background-color: black;");

        // Titolo "GameOver"
        Text title = new Text("Game Over");
        title.setFont(Font.font("Arial", 50));
        title.setFill(Color.RED);

        // Pulsante "Ritenta"
        Button retryButton = new Button(testoDialogo.getString("ritenta"));
        retryButton.setFont(Font.font("Arial", 20));
        retryButton.setOnAction(e -> metodoDelPulsante()); // Collega il pulsante al metodo

        // Layout verticale
        VBox layout = new VBox(20); // Spaziatura di 20 tra gli elementi
        layout.getChildren().addAll(title, retryButton);
        layout.setMinWidth(250);
        layout.setLayoutX(Menu.LARGHEZZA_AREA_GIOCO/2-layout.getMinWidth()/2);
        layout.setMinHeight(250);
        layout.setLayoutY(Menu.ALTEZZA_AREA_GIOCO/2-layout.getMinHeight()/2);
        
     
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Aggiungi il layout al pannello principale (AreaGioco)
        areaGioco.getChildren().addAll(layout);
        
        
    }
    public void metodoDelPulsante() {
		areaGioco.setId("MI FACCIO LE BAMBINE");
    }
}