package it.edu.iisgubbio.lovelace.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class OggettoPannello {
    Group finestra = new Group();

    public OggettoPannello(String testo, Image personaggio) {
        // Crea la finestra con lo sfondo
        Rectangle sfondo = new Rectangle(900, 300);
        sfondo.setFill(Color.rgb(0 , 0 , 0 , 0.7)); // Sfondo semitrasparente
        
        // Crea l'immagine del personaggio
        ImageView imgPersonaggio = new ImageView(personaggio);
        imgPersonaggio.setLayoutX(30);
        imgPersonaggio.setLayoutY(30);
        imgPersonaggio.setFitWidth(150);
        imgPersonaggio.setFitHeight(150);
        
        // Crea la TextArea per il dialogo
        TextArea dialogo = new TextArea();
        dialogo.setStyle("-fx-font-size: 18px;");
        dialogo.setPrefHeight(170); // Imposta un'altezza preferita
        dialogo.setPrefWidth(500);  // Imposta una larghezza preferita
        dialogo.setLayoutX(250);
        dialogo.setLayoutY(30);
        dialogo.setWrapText(true);
        dialogo.setEditable(false); // Impedisce la modifica diretta del testo
        
        // Aggiungi tutto alla finestra
        finestra.getChildren().add(sfondo);
        finestra.getChildren().add(dialogo);
        finestra.getChildren().add(imgPersonaggio);
        finestra.setLayoutY(450);
        
        // Chiamare la funzione per l'effetto di scrittura
        showTypingEffect(dialogo, testo);
    }

    // Funzione per gestire l'effetto di scrittura
    private void showTypingEffect(TextArea dialogo, String testoDialogo) {
        Timeline timeline = new Timeline();
        final StringBuilder textBuilder = new StringBuilder();
        final int[] index = {0};  // Indice per tenere traccia della posizione nel testo

        // Crea una KeyFrame per aggiungere un carattere ogni 100 millisecondi
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event -> {
            if (index[0] < testoDialogo.length()) {
                textBuilder.append(testoDialogo.charAt(index[0]));  // Aggiungi il carattere
                dialogo.setText(textBuilder.toString());             // Imposta il testo del dialogo
                index[0]++;  // Incrementa l'indice per il prossimo carattere
            }
        });

        // Aggiungi la KeyFrame alla Timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(testoDialogo.length());  // Si ferma quando tutto il testo è scritto
        timeline.setRate(1);  // Velocità dell'effetto
        timeline.play();  // Avvia l'animazione
    }

    // Metodo per ottenere la finestra
    public Group getFinestra() {
        return finestra;
    }
}