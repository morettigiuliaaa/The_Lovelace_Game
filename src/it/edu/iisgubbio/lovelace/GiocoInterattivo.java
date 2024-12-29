package it.edu.iisgubbio.lovelace;

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
	boolean arrivatosù=false;
	boolean arrivatogiù=true;
	
	Scene scena;
    Pane areaGioco = new Pane();
    Timeline timelineGioco;
    
    
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine
    
    
    private int frameIndex = 0; // Indice del frame corrente
    private static final String[] FRAMES = {
        "AdaFrame1.png",
        "AdaFrame2.png",
        "AdaFrame3.png",
        "AdaFrame4.png",
        "AdaFrame5.png",
        "AdaFrame6.png"
    };

    private ImageView adaImageView;

    public void movimentoAda(Pane areaGioco) {
        if (adaImageView == null) {
            // Inizializza ImageView con il primo frame
            adaImageView = new ImageView(new Image(FRAMES[0]));
            adaImageView.setFitWidth(100); // Dimensioni personalizzabili
            adaImageView.setFitHeight(150);
            areaGioco.getChildren().add(adaImageView);
        }

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(200), event -> {
                // Cambia il frame
                frameIndex = (frameIndex + 1) % FRAMES.length;
                adaImageView.setImage(new Image(FRAMES[frameIndex]));
            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE); // Ripete all'infinito
        timeline.play();
    }
    
    /**
	 * @return restituisce true quando il gioco è completato
	 */
	private void pigiato(KeyEvent evento) {
		
		if (evento.getCode() == KeyCode.D || evento.getCode() == KeyCode.RIGHT) {
			System.out.println("d");
			double posizione = adaferma.getX();
			adaferma.setX(posizione+2.0);
			movimentoAda(areaGioco);
	       // Avanza
	    }
	    if (evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT) {
	    	System.out.println("a");
	    	double posizione = adaferma.getX();
			adaferma.setX(posizione-2.0);
			movimentoAda(areaGioco);
	       // Indietreggia
	    }
	    if (evento.getCode() == KeyCode.W || evento.getCode() == KeyCode.UP) {
	    	System.out.println("w");
	       // Salta
	    }
	}
	
	
	public GiocoInterattivo(Scene scenaPrimaria, RaccoltaTesti testoDialogo) {
		//andiamo a settare la lingua in OggettoPannello
		OggettoPannello.setTestoDialogo(testoDialogo);
		scena=scenaPrimaria;
		areaGioco=(Pane)scena.getRoot();
		Menu.finestra.setScene(scena);
		timelineGioco=new Timeline(new KeyFrame(
				Duration.millis(100),
				x -> cambioScena()));
		timelineGioco.setCycleCount(Animation.INDEFINITE);
		timelineGioco.play();
	}
	private void cambioScena() {
		int nElemnti=areaGioco.getChildren().size();
		//prendo lultimo elemento aggiunto alla lista
		Group gruppo=(Group)areaGioco.getChildren().get(nElemnti-1);
		if(gruppo.getOpacity()>=1) {
			//aggiorniamo la timeline
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			time=System.currentTimeMillis();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> evoluzioneGioco()));
			timelineGioco.play();
			gioco(gruppo);
		}
	}
	
	public void evoluzioneGioco() {
		//così il gioco è finito dopo 2000mS la condizione dipende dallo sviluppo del gioco
				if(System.currentTimeMillis()-time>300000) {
					System.out.println("completato");
					completato=true;
					timelineGioco.stop();
				}
	}

	public void gioco(Group gruppo) {
        areaGioco.getChildren().clear();
        gruppo.getChildren().add(adaferma);
        areaGioco.getChildren().add(gruppo);
        (new FadeOut(gruppo, 1000)).start();
        cambioalgioco(gruppo);
        
	}
	
	
	public void cambioalgioco(Group gruppo) {
		(new FadeOut(gruppo, 1000)).start();
		areaGioco.setId("gioco1");
        adaferma.resize(100, 100);
		
		adaferma.setLayoutX(40);  // Imposta la posizione orizzontale
        adaferma.setLayoutY(170); 
        scena.setOnKeyPressed(e -> pigiato(e));// 
	}
	
	boolean completato=false;
	public boolean isCompletato() {
		return completato;
	}
	long time; //si può eliminare
	
	
}
