package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.*;
import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GiocoInterattivo {
    private boolean arrivatosù = false;
    private boolean arrivatogiù = true;

    private Scene scena;
    private Pane areaGioco = new Pane();
    private Timeline timelineGioco;

    private Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    private ImageView adaferma = new ImageView(adaImage); // Immagine ferma
    
    private int frameIndex = 0; // Indice frame corrente
    private static final String[] FRAMES = {
        GiocoInterattivo.class.getResource("AdaFrame1.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame2.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame3.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame4.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame5.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame6.png").toExternalForm()
    };

    private ImageView adaImageView;
    private Timeline movimentoTimeline;  // Timeline per l'animazione
    private boolean isMoving = false;  // Indica se Ada è in movimento

    public GiocoInterattivo(Scene scenaPrimaria, RaccoltaTesti testoDialogo) {
        // Inizializzazione
        OggettoPannello.setTestoDialogo(testoDialogo);
        scena = scenaPrimaria;
        areaGioco = (Pane) scena.getRoot();
        Menu.finestra.setScene(scena);
        timelineGioco = new Timeline(new KeyFrame(Duration.millis(100), x -> cambioScena(testoDialogo)));
        timelineGioco.setCycleCount(Animation.INDEFINITE);
        timelineGioco.play();
    }

//    // Metodo per il movimento in avanti (animazione)
//    public void movimentoAdaAvanti(Pane areaGioco) {
//        if (adaImageView == null) {
//            adaImageView = new ImageView(new Image(FRAMES[0]));
//            adaImageView.setFitWidth(100);
//            adaImageView.setFitHeight(150);
//            areaGioco.getChildren().add(adaImageView);
//        }
//
//        // Avvio dell'animazione in avanti
//        movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(700), event -> {
//            frameIndex = (frameIndex + 1) % FRAMES.length;  // Incrementa in modo ciclico
//            adaImageView.setImage(new Image(FRAMES[frameIndex]));
//        }));
//
//        movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
//        movimentoTimeline.play();
//        isMoving = true;  // Imposta stato in movimento
//    }
//
//    // Metodo per il movimento indietro (animazione)
//    public void movimentoAdaDietro(Pane areaGioco) {
//        if (adaImageView == null) {
//            adaImageView = new ImageView(new Image(FRAMES[5])); // Inizia dall'ultimo frame
//            adaImageView.setFitWidth(100);
//            adaImageView.setFitHeight(150);
//            areaGioco.getChildren().add(adaImageView);
//        }
//
//        // Avvio dell'animazione indietro
//        movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(700), event -> {
//            frameIndex = (frameIndex - 1 + FRAMES.length) % FRAMES.length;  // Decrementa in modo ciclico
//            adaImageView.setImage(new Image(FRAMES[frameIndex]));
//        }));
//
//        movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
//        movimentoTimeline.play();
//        isMoving = true;  // Imposta stato in movimento
//    }

    // Gestisce la pressione dei tasti
    private void pigiato(KeyEvent evento) {
        if ((evento.getCode() == KeyCode.D || evento.getCode() == KeyCode.RIGHT) && !isMoving) {
            double posizione = adaferma.getX();
            adaferma.setX(posizione + 2.0);
//            movimentoAdaAvanti(areaGioco); // Avvia animazione se non è già in movimento
        } else {
              movimentoTimeline.stop();  // Ferma la timeline
              areaGioco.getChildren().clear();
              areaGioco.getChildren().add(adaferma);  // Mostra immagine statica
             }
        if ((evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT) && !isMoving) {
            double posizione = adaferma.getX();
            adaferma.setX(posizione - 2.0);
//            movimentoAdaDietro(areaGioco); // Avvia animazione se non è già in movimento
        }else {
        	movimentoTimeline.stop();  // Ferma la timeline
            areaGioco.getChildren().clear();
            areaGioco.getChildren().add(adaferma);  // Mostra immagine statica
        }
    }

    // Metodo per cambiare scena (continuazione logica del gioco)
    private void cambioScena(RaccoltaTesti testoDialogo) {
        int nElemnti = areaGioco.getChildren().size();
        Group gruppo = (Group) areaGioco.getChildren().get(nElemnti - 1);
        if (gruppo.getOpacity() >= 1) {
            timelineGioco.stop();
            timelineGioco.getKeyFrames().clear();
            time = System.currentTimeMillis();
            timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> evoluzioneGioco(testoDialogo)));
            timelineGioco.play();
            gioco(gruppo);
        }
    }

    public void evoluzioneGioco(RaccoltaTesti testoDialogo) {
    	Domande domande = new Domande(areaGioco);

        // Mostra una domanda all'inizio
        domande.mostraDomanda1(testoDialogo);
        if (System.currentTimeMillis() - time > 300000) {
            System.out.println("completato");
            completato = true;
            timelineGioco.stop();
        }
    }

    public void gioco(Group gruppo) {
        areaGioco.getChildren().clear();
        areaGioco.getChildren().add(gruppo);
        (new FadeOut(gruppo, 1000)).start();
        cambioalgioco(gruppo);
    }

    public void cambioalgioco(Group gruppo) {
    	(new FadeOut(gruppo, 1000)).start();
    	areaGioco.setId("gioco1");
        adaferma.resize(500, 600);
        adaferma.setLayoutX(60);
        adaferma.setLayoutY(700);
        adaferma.toFront();
        areaGioco.getChildren().add(adaferma);
        
        
        scena.setOnKeyPressed(e -> pigiato(e));  // Gestisce la pressione del tasto
    }

    boolean completato = false;

    public boolean isCompletato() {
        return completato;
    }

    long time;
}
