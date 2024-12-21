package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
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
	
	private void loop() {
		if(dialogo.statoDiscorso()) {
			System.out.println("dialogo concluso");
		}else {
			//aggiorniamo il tempo fino a quando non si interrompe l'animazione
			tempoCambioDialogo=System.currentTimeMillis();
		}
		if(System.currentTimeMillis()-tempoCambioDialogo>1000 && dialogo.statoDiscorso()) {
			nDialogo++;
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
	
	private void cambioScena() {
		int nElemnti=areaGioco.getChildren().size();
		//prendo lultimo elemento aggiunto alla lista
		Group gruppo=(Group)areaGioco.getChildren().get(nElemnti-1);
		gruppo.getOpacity();
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
		ObservableList<Node> elementiSchermata= areaGioco.getChildren();
		int i=0;
		try {
			while(elementiSchermata.get(i)!=null) {
				i++;
			}
		} catch (IndexOutOfBoundsException e) {
			i=i-1;
		}
		//rimuoviamo lultimo node
		areaGioco.getChildren().remove(i);
		areaGioco.getChildren().add(dialogo.getFinestra());
	}
}
