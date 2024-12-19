package it.edu.iisgubbio.lovelace;

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
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start{
	Scene scena;
	
	RaccoltaTesti testoDialogo;
		
	//Oggetti
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    
    //oggetti interfaccia
    Pane areaGioco = new Pane();
    ImageView augusto = new ImageView(augustoImage); // Caricamento corretto dell'immagine
    ImageView adaferma = new ImageView(adaImage); // Caricamento corretto dell'immagine
    
	Timeline timelineGioco;
	public Start(Stage finestra, Scene scenaPrimaria) {
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
		testoDialogo= new RaccoltaTesti(Locale.ENGLISH);
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
        
        areaGioco.getChildren().add((new OggettoPannello( testoDialogo.getString("dialogo1adaen") , adaImage)).getFinestra());
        
        
      
        
        
        
        // AUGUSTO E' DA RIMPICCIOLIRE
        
    }
}
