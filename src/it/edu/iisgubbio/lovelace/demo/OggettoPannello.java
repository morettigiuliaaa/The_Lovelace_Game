package it.edu.iisgubbio.lovelace.demo;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class OggettoPannello {
	Group finestra = new Group();
	public OggettoPannello(String testo, Image personaggio) {
		Rectangle sfondo = new Rectangle(850, 300);
		ImageView imgPersonaggio = new ImageView(personaggio);
		TextArea dialogo = new TextArea(testo);
		
		finestra.getChildren().add(sfondo);
        
		finestra.getChildren().add(imgPersonaggio);
		imgPersonaggio.setLayoutX(20);
        imgPersonaggio.setLayoutY(20);
        imgPersonaggio.setFitWidth(150);
        imgPersonaggio.setFitHeight(150);
        
		finestra.getChildren().add(dialogo);
		dialogo.setLayoutX(200);
        dialogo.setLayoutY(50);
        dialogo.setWrapText(true);
	}
	public Group getFinestra(){
		return finestra;
	}
}
