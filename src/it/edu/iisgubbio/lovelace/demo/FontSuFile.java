package it.edu.iisgubbio.lovelace.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FontSuFile extends Application{

    @Override
    public void start(Stage finestra) throws Exception {
        Label testo = new Label("Hello world!");
        Label testoCss = new Label("Hello world (css)!");

        BorderPane principale = new BorderPane();
        principale.setCenter(testo);
        principale.setBottom(testoCss);

        testoCss.setId("tcss");

        // https://www.tutorialspoint.com/how-to-add-custom-fonts-to-a-text-in-javafx
        Font font = Font.loadFont(FontSuFile.class.getResourceAsStream("../font.ttf"), 45);
        testo.setFont(font);

        Scene scena = new Scene(principale, 300, 250);
        scena.getStylesheets().add("it/edu/iisgubbio/lovelace/demo/stili.css");
        finestra.setTitle("Hello World!");
        finestra.setScene(scena);
        finestra.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
