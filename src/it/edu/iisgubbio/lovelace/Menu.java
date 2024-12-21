package it.edu.iisgubbio.lovelace;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;

import it.edu.iisgubbio.lovelace.dynamicEffects.*;

public class Menu extends Application {
    // Definizione degli oggetti UI
    Label lingua = new Label();
    Label audio = new Label();
    Pane areaGioco = new Pane();
    Label eTitolo = new Label("The Lovelace Game");
    Label eSottoTitolo = new Label("Aiuta Ada a costruire il suo algoritmo!");
    Button bInizio = new Button("Inizio");
    Button bImpostazioni = new Button("Impostazioni");
    Button bEsci = new Button("Esci");
    Button bTornaAlMenu = new Button("🏠");
    Group home = new Group();

    // Caricamento della musica di sottofondo
    String filePathHOME = "LovelaceGameInGame.wav";
    Media music = new Media(new File(filePathHOME).toURI().toString());
    MediaPlayer audioClip = new MediaPlayer(music);

    // Caricamento delle immagini dei personaggi
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine

    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine

    final int LARGHEZZA_AREA_GIOCO = 900;
    final int ALTEZZA_AREA_GIOCO = 700;

    // ToggleSwitch globale per la lingua
    ToggleSwitch button = new ToggleSwitch();

    RaccoltaTesti testoDialogo;

    @Override
    public void start(Stage finestra) throws Exception {
    	//impostiamo la lingua del testo in italiano
        testoDialogo = new RaccoltaTesti(Locale.ITALIAN);
    	
        // Riproduzione della musica di sottofondo
        audioClip.setCycleCount(Animation.INDEFINITE);
        audioClip.play();

        // Aggiunta degli elementi alla schermata principale
        home.getChildren().add(eTitolo);
        home.getChildren().add(eSottoTitolo);
        home.getChildren().add(bInizio);
        home.getChildren().add(bImpostazioni);
        home.getChildren().add(bEsci);

        areaGioco.getChildren().add(home);

        // Impostazioni del layout degli elementi sulla schermata
        eTitolo.setId("titolo");
        eSottoTitolo.setId("sottotitolo");

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

        // Gestione degli eventi dei pulsanti
        bImpostazioni.setOnAction(e -> impostazioni());
        bTornaAlMenu.setOnAction(e -> tornaHome());
        bEsci.setOnAction(e -> esci());

        // Impostazione dell'icona della finestra
        Image icon = new Image(getClass().getResourceAsStream("IMG_2263.jpeg"));
        finestra.getIcons().add(icon);

        // Creazione della scena con gli stili CSS
        eTitolo.setId("titolo");
        areaGioco.setId("paneSfondo");

        Scene scena = new Scene(areaGioco, LARGHEZZA_AREA_GIOCO, ALTEZZA_AREA_GIOCO);
        bInizio.setOnAction(e -> inizioGioco(finestra, scena));
        scena.getStylesheets().add("it/edu/iisgubbio/lovelace/foglio.css");

        finestra.setTitle("The Lovelace Game!");
        finestra.setResizable(false);
        finestra.setScene(scena);
        finestra.show();
    }

    // Funzione per tornare alla schermata principale
    public void tornaHome() {
        areaGioco.getChildren().clear();
        home.getChildren().clear();
        home.getChildren().add(eTitolo);
        home.getChildren().add(eSottoTitolo);
        home.getChildren().add(bInizio);
        home.getChildren().add(bImpostazioni);
        home.getChildren().add(bEsci);

        areaGioco.getChildren().add(home);

        // Effetto di transizione con FadeIn
        (new FadeIn(home, 1000, 30)).start();

        // Ripristino delle posizioni degli elementi sulla schermata principale
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

    // Funzione per gestire le impostazioni (lingua e volume)
    public void impostazioni() {
        Slider slideraudio = new Slider(0, 1, 1);
        Group effetto = new Group();
        lingua.setId("tcss");
        audio.setId("tcss");
        //settiamo tutti gli elementi con la stessa larghezza del ToggleSwitch
        slideraudio.setMinWidth(220);
        audio.setMinWidth(220);
        lingua.setMinWidth(220);
        
        effetto.getChildren().add(bTornaAlMenu);
        effetto.getChildren().add(button);
        effetto.getChildren().add(slideraudio);
        slideraudio.setId("slider");
        audio.setId("impostazioni");
        lingua.setId("impostazioni");
        audio.setLayoutX(331);
        audio.setLayoutY(100);
        slideraudio.setLayoutX(331);
        slideraudio.setLayoutY(150);
        button.setLayoutX(331);
        button.setLayoutY(250);
        lingua.setLayoutX(331);
        lingua.setLayoutY(200);
        
        // Effetto di transizione per le impostazioni
        (new FadeIn(effetto, 1000)).start();
        areaGioco.getChildren().clear();
        areaGioco.setId("paneSfondo");

        // Verifica della lingua selezionata tramite il ToggleSwitch
        if (button.switchOnProperty().getValue()) {
            testoDialogo.setLocale(Locale.ENGLISH);
            eSottoTitolo.setLayoutX(500); 
            eSottoTitolo.setLayoutY(190);
        } else {
            testoDialogo.setLocale(Locale.ITALIAN);
        }
        lingua.setText(testoDialogo.getString("lingua"));
        audio.setText(testoDialogo.getString("audio"));

        effetto.getChildren().add(lingua);
        effetto.getChildren().add(audio);
        
        bTornaAlMenu.setLayoutX(10);
        bTornaAlMenu.setLayoutY(7);

        // Gestione del volume tramite slider
        slideraudio.setBlockIncrement(0.1);
        slideraudio.valueProperty().addListener((observable, oldValue, newValue) -> {
            audioClip.setVolume(newValue.doubleValue());
            System.out.println(newValue);
        });

        // Gestione del cambiamento della lingua
        button.switchOnProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("cambiato in " + newValue);
            if (newValue) {
                testoDialogo.setLocale(Locale.ENGLISH);
            } else {
                testoDialogo.setLocale(Locale.ITALIAN);
            }
            lingua.setText(testoDialogo.getString("lingua"));
            audio.setText(testoDialogo.getString("audio"));
            aggiornaLinguaSuInterfaccia();
        });

        // Aggiunta delle impostazioni alla schermata
        areaGioco.getChildren().add(effetto);
    }

    // Funzione per aggiornare la lingua dell'interfaccia
    void aggiornaLinguaSuInterfaccia() {
        eSottoTitolo.setText(testoDialogo.getString("sottotitolo"));
        bInizio.setText(testoDialogo.getString("inizio"));
        bImpostazioni.setText(testoDialogo.getString("impostazioni"));
        bEsci.setText(testoDialogo.getString("esci"));
    }

    // Funzione per iniziare il gioco
    public void inizioGioco(Stage finestra, Scene scena) {
        audioClip.stop();

        // Creazione del rettangolo di transizione
        Rectangle rettangolo = new Rectangle(LARGHEZZA_AREA_GIOCO, ALTEZZA_AREA_GIOCO);
        rettangolo.setFill(Color.BLACK);
        Group gruppo = new Group(rettangolo);
        areaGioco.getChildren().add(gruppo);
        (new FadeIn(gruppo, 2000)).start();

        // Avvio del gioco
        @SuppressWarnings("unused")
        Start gioco = new Start(finestra, scena, testoDialogo);
    }

    // Funzione per uscire dal gioco
    public void esci() {
        Platform.exit();
    }

    // Funzione main per avviare l'applicazione
    public static void main(String args[]) {
        launch();
    }
}
