package it.edu.iisgubbio.lovelace;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

import it.edu.iisgubbio.lovelace.demo.FontSuFile;
import it.edu.iisgubbio.lovelace.dynamicEffects.*;

public class Interfaccia extends Application {
    Pane areaGioco = new Pane();
    Label eTitolo = new Label("The Lovelace Game");
    Label eSottoTitolo = new Label("Aiuta Ada a costruire il suo algoritmo!");
    Button bInizio = new Button("Inizio");
    Button bImpostazioni = new Button("Impostazioni");
    Button bEsci = new Button("Esci");
    Button bTornaAlMenu = new Button("🏠");
    Group home = new Group();

    String filePath = "LovelaceGameInGame.wav";
    AudioClip audioClip = new AudioClip(new File(filePath).toURI().toString());
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine
    
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine
    
    final int LARGHEZZA_AREA_GIOCO = 900;
    final int ALTEZZA_AREA_GIOCO = 700;

    public void start(Stage finestra) throws Exception {
        audioClip.play();
        
        home.getChildren().add(eTitolo);
        home.getChildren().add(eSottoTitolo);
        home.getChildren().add(bInizio);
        home.getChildren().add(bImpostazioni);
        home.getChildren().add(bEsci);
        
        areaGioco.getChildren().add(home);
        
        eTitolo.setId("titolo");
        eSottoTitolo.setId("sottotitolo");
        
        (new FadeIn(home, 1000, 30)).start();
        
        eTitolo.setLayoutX(150);
        eTitolo.setLayoutY(100);
        
        eSottoTitolo.setLayoutX(280);
        eSottoTitolo.setLayoutY(190);
        
        bInizio.setLayoutX(342);
        bInizio.setLayoutY(250);
        
        bImpostazioni.setLayoutX(342);
        bImpostazioni.setLayoutY(310);
        
        bEsci.setLayoutX(342);
        bEsci.setLayoutY(373);
        
        bInizio.setPrefWidth(200);
        bImpostazioni.setPrefWidth(200);
        bEsci.setPrefWidth(200);
        
        bInizio.setOnAction(e -> inizioGioco());
        bImpostazioni.setOnAction(e -> impostazioni());
        bTornaAlMenu.setOnAction(e -> tornaHome());        
        bEsci.setOnAction(e -> esci());
        
        Image icon = new Image(getClass().getResourceAsStream("IMG_2263.jpeg"));
        finestra.getIcons().add(icon);
        
        eTitolo.setId("titolo");
        areaGioco.setId("paneSfondo");
        
        Scene scena = new Scene(areaGioco, LARGHEZZA_AREA_GIOCO, ALTEZZA_AREA_GIOCO);
        scena.getStylesheets().add("it/edu/iisgubbio/lovelace/foglio.css");
        
        finestra.setTitle("The Lovelace Game!");
        finestra.setResizable(false);
        finestra.setScene(scena);
        finestra.show();
    }

    public void inizioGioco() {
        areaGioco.getChildren().clear();
        audioClip.stop();
        areaGioco.setId("dialogo");

        // Posiziona l'immagine sopra il "pane"
        adaferma.setLayoutX(40);  // Imposta la posizione orizzontale
        adaferma.setLayoutY(170); // Imposta la posizione verticale

        // Aggiungi l'immagine al "pane principale"
        areaGioco.getChildren().add(adaferma);
        
        // Posiziona l'immagine sopra il "pane"
        augusto.setLayoutX(380);  // Imposta la posizione orizzontale
        augusto.setLayoutY(170); // Imposta la posizione verticale

        // Aggiungi l'immagine al "pane principale"
        areaGioco.getChildren().add(augusto);
        
        // AUGUSTO E' DA RIMPICCIOLIRE
        
    }

    public void tornaHome() {
        areaGioco.getChildren().clear();
        home.getChildren().clear();
        home.getChildren().add(eTitolo);
        home.getChildren().add(eSottoTitolo);
        home.getChildren().add(bInizio);
        home.getChildren().add(bImpostazioni);
        home.getChildren().add(bEsci);
        
        areaGioco.getChildren().add(home);
        
        (new FadeIn(home, 1000, 30)).start();
        
        eTitolo.setLayoutX(150);
        eTitolo.setLayoutY(100);
        
        eSottoTitolo.setLayoutX(280);
        eSottoTitolo.setLayoutY(190);
        
        bInizio.setLayoutX(342);
        bInizio.setLayoutY(250);
        
        bImpostazioni.setLayoutX(342);
        bImpostazioni.setLayoutY(310);
        
        bEsci.setLayoutX(342);
        bEsci.setLayoutY(373);
        
        bInizio.setPrefWidth(200);
        bImpostazioni.setPrefWidth(200);
        bEsci.setPrefWidth(200);
    }

    public void impostazioni() {
        Slider slideraudio = new Slider(0, 100, 100);
        Group effetto = new Group();
        Label lingua = new Label("Lingua:");
        lingua.setId("tcss");
        Label audio = new Label("Audio:");
        audio.setId("tcss");
        ToggleSwitch button = new ToggleSwitch();
        effetto.getChildren().add(bTornaAlMenu);
        effetto.getChildren().add(button);
        effetto.getChildren().add(slideraudio);
        effetto.getChildren().add(lingua);
        effetto.getChildren().add(audio);
        slideraudio.setId("slider");
        audio.setId("impostazioni");
        lingua.setId("impostazioni");
        audio.setLayoutX(410);
        audio.setLayoutY(100);
        slideraudio.setLayoutX(375);
        slideraudio.setLayoutY(150);
        button.setLayoutX(331);
        button.setLayoutY(250);
        lingua.setLayoutX(395);
        lingua.setLayoutY(200);
        (new FadeIn(effetto, 1000)).start();
        areaGioco.getChildren().clear();
        areaGioco.setId("paneSfondo");
        areaGioco.getChildren().add(effetto);
        bTornaAlMenu.setLayoutX(10);
        bTornaAlMenu.setLayoutY(7);
        slideraudio.setBlockIncrement(1);
        slideraudio.valueProperty().addListener((observable, oldValue, newValue) -> {
            audioClip.setVolume(newValue.doubleValue());
        });
    }

    public void esci() {
        Platform.exit();
    }

    public static void main(String args[]) {
        launch();
    }
}