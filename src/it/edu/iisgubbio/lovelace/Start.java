package it.edu.iisgubbio.lovelace;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import it.edu.iisgubbio.lovelace.*;
import it.edu.iisgubbio.lovelace.demo.*;
import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start{
	Scene scena;
	
	RaccoltaTesti testoDialogo;
	
	String filePathINIZIO = "suonoIniziale.wav";
	Media music= new Media(new File(filePathINIZIO).toURI().toString());
	MediaPlayer audioClip= new MediaPlayer(music);
	
	//Oggetti
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    
    //oggetti interfaccia
    Pane areaGioco = new Pane();
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine
    
	Timeline timelineGioco;
	public Start(Stage finestra, Scene scenaPrimaria) {
		testoDialogo=new RaccoltaTesti(Locale.ITALIAN);
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
		audioClip.play();
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
        
        areaGioco.getChildren().add((new OggettoPannello( testoDialogo.getString("dialogo1ada") , adaImage)).getFinestra());
        
        
      
        
        
        
        // AUGUSTO E' DA RIMPICCIOLIRE
        
    }
}
