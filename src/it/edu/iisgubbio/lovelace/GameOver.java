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

    public GameOver(Pane areaGioco, RaccoltaTesti testoDialogo, Group gruppo) {
        // Imposta lo sfondo nero dell'AreaGioco
        areaGioco.setStyle("-fx-background-color: black;");

        // Titolo "GameOver"
        Text title = new Text("Game Over");
        title.setFont(Font.font("Arial", 50));
        title.setFill(Color.RED);

        // Pulsante "Ritenta"
        Button retryButton = new Button(testoDialogo.getString("ritenta"));
        retryButton.setFont(Font.font("Arial", 20));
        //retryButton.setOnAction(e -> metodoDelPulsante()); // Collega il pulsante al metodo

        // Layout verticale
        VBox layout = new VBox(20); // Spaziatura di 20 tra gli elementi
        layout.getChildren().addAll(title, retryButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Aggiungi il layout al pannello principale (AreaGioco)
        areaGioco.getChildren().addAll(areaGioco, layout);
    }
}