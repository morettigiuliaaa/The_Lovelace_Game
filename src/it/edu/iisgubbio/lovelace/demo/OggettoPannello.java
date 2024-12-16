package it.edu.iisgubbio.lovelace.demo;

import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OggettoPannello {
	Group finestra = new Group();
	public OggettoPannello(String testo, Image personaggio) {
		Rectangle sfondo = new Rectangle(900, 300);
		ImageView imgPersonaggio = new ImageView(personaggio);
		TextArea dialogo = new TextArea(testo);
		finestra.getChildren().add(sfondo);
		finestra.getChildren().add(dialogo);
		dialogo.setLayoutX(250);
        dialogo.setLayoutY(30);
        dialogo.setWrapText(true);		
        dialogo.setEditable(false);
		sfondo.setFill(Color.rgb(0 , 0 , 0 , 0.7));
		finestra.getChildren().add(imgPersonaggio);
		imgPersonaggio.setLayoutX(30);
        imgPersonaggio.setLayoutY(30);
        imgPersonaggio.setFitWidth(150);
        imgPersonaggio.setFitHeight(150);
        finestra.setLayoutY(450);
        
        
		
	}
	public Group getFinestra(){
		return finestra;
	}
}
