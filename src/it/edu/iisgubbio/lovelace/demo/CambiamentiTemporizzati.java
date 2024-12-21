package it.edu.iisgubbio.lovelace.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/****************************************************************************
 * Scrive messaggi... uno ogni 3 secondi
 ***************************************************************************/
public class CambiamentiTemporizzati extends Application{

    Label tizio = new Label("tizio");
    Label caio = new Label("caio");
    int tic=0;

    // mi invento un dialogo
    TestoDove dialogo[] = {
            new TestoDove("ciao Caio!", tizio),
            new TestoDove("ciao Tizio!", caio),
            new TestoDove("docve si va?", caio),
            new TestoDove("non lo so", tizio)
    };

    @Override
    public void start(Stage finestra) throws Exception {
        GridPane principale = new GridPane();
        principale.add(new Label("tizio"), 0, 0);
        principale.add(new Label("caio"), 1, 0);
        principale.add(tizio, 0, 1);
        principale.add(caio, 1, 1);

        principale.setHgap(10);
        principale.setVgap(10);
        tizio.setPrefWidth(200);
        caio.setPrefWidth(200);
        Scene scena = new Scene(principale);
        finestra.setTitle("dialogo!");
        finestra.setScene(scena);
        finestra.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3), // ogni quanto va chiamata la funzione
                x -> aggiornaTimer()));
        timeline.setCycleCount(dialogo.length);
        timeline.play();
    }

    void aggiornaTimer() {
        System.out.println(tic);
        dialogo[tic].dove.setText(dialogo[tic].testo);
        tic++;
    }

    public static void main(String[] args) {
        launch(args);
    }
}