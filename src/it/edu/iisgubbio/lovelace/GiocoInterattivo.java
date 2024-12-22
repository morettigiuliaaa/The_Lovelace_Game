package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GiocoInterattivo {
	
	Scene scena;
    Pane areaGioco = new Pane();
    Timeline timelineGioco;
    
    /**
	 * @return restituisce true quando il gioco è completato
	 */
	private void evoluzioneGioco() {
		
		/*******************************
		*scrivere qui il codice del gioco
		********************************/
		
		//così il gioco è finito dopo 2000mS la condizione dipende dallo sviluppo del gioco
		if(System.currentTimeMillis()-time>2000) {
			System.out.println("completato");
			completato=true;
			timelineGioco.stop();
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
	
	public void gioco(Group gruppo) {
        areaGioco.getChildren().clear();
        areaGioco.getChildren().add(gruppo);
        (new FadeOut(gruppo, 2000)).start();
	}
	
	
	boolean completato=false;
	public boolean isCompletato() {
		return completato;
	}
	long time; //si può eliminare
	
	
}
