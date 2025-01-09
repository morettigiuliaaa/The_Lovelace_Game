package it.edu.iisgubbio.lovelace;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NumeriPergamene {
	int nPergameneTrovate;
	private static final String immaginiPergamena[]= {
			NumeriPergamene.class.getResource("PergamenaIntera.png").toExternalForm(),
			NumeriPergamene.class.getResource("Pergamena1.png").toExternalForm(),
			NumeriPergamene.class.getResource("Pergamena2.png").toExternalForm(),
			NumeriPergamene.class.getResource("PergamenaVuota.png").toExternalForm(),
	};
	public NumeriPergamene(){
		nPergameneTrovate=0;
	}
	private Image selezionaImmagine() {
		if(nPergameneTrovate==0) {
			return new Image(immaginiPergamena[3]);
		}else if(nPergameneTrovate==1) {
			return new Image(immaginiPergamena[2]);
		}else if(nPergameneTrovate==2) {
			return new Image(immaginiPergamena[1]);
		}else {
			return new Image(immaginiPergamena[0]);
		}
	}
	public Group getView() {
		Group gruppo = new Group();
		Label nPergamene= new Label(nPergameneTrovate+"/3");
		nPergamene.setId("pergamene");
		gruppo.getChildren().addAll(new ImageView(selezionaImmagine()), nPergamene);
		return gruppo;
	}
}
