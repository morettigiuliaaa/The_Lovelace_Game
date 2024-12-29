package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.dynamicEffects.FadeIn;
import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start{
	Scene scena;
	
	RaccoltaTesti testoDialogo;
	
	AudioClip suonoIniziale = new AudioClip(getClass().getResource("suonoIniziale.wav").toString());
	
	//Oggetti
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    
    //oggetti interfaccia
    Pane areaGioco = new Pane();
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine
    
	Timeline timelineGioco;
	
	OggettoPannello dialogo;
	
	long tempoCambioDialogo=0;
	int nDialogo=0;
	
	public Start(Stage finestra, Scene scenaPrimaria, RaccoltaTesti testoDialogo) {
		this.testoDialogo=testoDialogo;
		//andiamo a settare la lingua in OggettoPannello
		OggettoPannello.setTestoDialogo(testoDialogo);
		suonoIniziale.setVolume(Menu.volume);
		suonoIniziale.play();
		scena=scenaPrimaria;
		areaGioco=(Pane)scena.getRoot();
		finestra.setScene(scena);
		timelineGioco=new Timeline(new KeyFrame(
				Duration.millis(100),
				x -> cambioScena()));
		timelineGioco.setCycleCount(Animation.INDEFINITE);
		timelineGioco.play();
	}
	
	boolean giocoInterattivo=false; //indica se il gioco è interattivo oppure ci sono i dialoghi
	boolean completato=false; //variabile gestita all'interno del metodo evoluzioneGioco che indica se la fase interattiva è completata
	boolean transizione=true; //inidica se bisogna fare la transizione
	Group gruppo;
	GiocoInterattivo gioco;
	

	private void loop() {
		if(!dialogo.statoDiscorso()) {
			//aggiorniamo il tempo fino a quando non si interrompe l'animazione
			tempoCambioDialogo=System.currentTimeMillis();
		}
		if(System.currentTimeMillis()-tempoCambioDialogo>1000 && dialogo.statoDiscorso() || giocoInterattivo) {
			if(!giocoInterattivo) {
				nDialogo++;
				//il 4 corrisponde al numero dei dialoghi della prima parte
				if(nDialogo==4 && !completato) {
					giocoInterattivo=true;
				}
			}
			if(giocoInterattivo) {
				if(transizione) {
					// Creazione del rettangolo di transizione
					Rectangle rettangolo = new Rectangle(Menu.LARGHEZZA_AREA_GIOCO, Menu.ALTEZZA_AREA_GIOCO);
					rettangolo.setFill(Color.BLACK);
					Group gruppo = new Group(rettangolo);
					areaGioco.getChildren().add(gruppo);
					transizione=false;
					(new FadeIn(gruppo, 3000)).start();
					gioco = new GiocoInterattivo(scena, testoDialogo);

				}
				completato=gioco.isCompletato();
				if(completato) {
					giocoInterattivo=false;
					nDialogo--;
				}
			}else {
				try{
					dialogo=new OggettoPannello(nDialogo);
					System.out.println("cambio dialogo");
					cambiaDialogo();
				}catch (ArrayIndexOutOfBoundsException e) {
					//Dialoghi terminati
					System.out.println("Dialoghi terminati");
					timelineGioco.stop();
				}
			}
		}
	}

	private void cambioScena() {
		int nElemnti=areaGioco.getChildren().size();
		//prendo lultimo elemento aggiunto alla lista
		Group gruppo=(Group)areaGioco.getChildren().get(nElemnti-1);
		if(gruppo.getOpacity()>=1) {
			//aggiorniamo la timeline
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> loop()));
			timelineGioco.play();
			start(gruppo);
		}
	}
	
	public void start(Group gruppo) {
        areaGioco.getChildren().clear();
        
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
        
        areaGioco.getChildren().add(gruppo);
        (new FadeOut(gruppo, 2000)).start();
        
		dialogo=new OggettoPannello(nDialogo);
		areaGioco.getChildren().add(dialogo.getFinestra());
		
		
		
//      TODO: AUGUSTO E' DA RIMPICCIOLIRE
    }
	
	
	private void cambiaDialogo() {
		areaGioco.getChildren().remove(areaGioco.getChildren().size()-1);
		areaGioco.getChildren().add(dialogo.getFinestra());
	}
}
