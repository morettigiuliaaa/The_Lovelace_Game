package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class End {
	private Pane areaGioco;
	boolean fadeOut;
	RaccoltaTesti testoDialogo;
	Timeline timeline;
	public End(RaccoltaTesti testoDialogo, Group gruppo) {
		fadeOut=false;
		areaGioco=(Pane)Menu.finestra.getScene().getRoot();
		this.testoDialogo=testoDialogo;
		KeyFrame keyFrame=new KeyFrame(Duration.millis(50), e->{
			if(gruppo.getOpacity()>1.0) {
				fadeOut=true;
				(new FadeOut(gruppo, 2000)).start();
			}else if(fadeOut){
				timeline.stop();
				cambiaPane();
			}
		});
		timeline=new Timeline(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	private void cambiaPane() {
		areaGioco.getChildren().clear();
		areaGioco.setId("paneSfondo");VBox layout = new VBox(20); // Spaziatura di 20 tra gli elementi// Titolo "GameOver"
        Label title = new Label(testoDialogo.getString("fine"));
        title.setId("titoloEnd");
        title.setTextFill(Color.WHITE);
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(450);
        Rectangle sfondo= new Rectangle(Menu.LARGHEZZA_AREA_GIOCO,Menu.ALTEZZA_AREA_GIOCO);
        sfondo.setFill(Color.rgb(0, 0, 0, 0.5));
        Button exitButton = new Button(testoDialogo.getString("esci"));
        Button menuButton = new Button("Menu");
        menuButton.setOnAction(e->{
        	Menu menu=new Menu();
	        try {
				menu.start(Menu.finestra);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        exitButton.setPrefWidth(200);
        menuButton.setPrefWidth(200);
        exitButton.setFont(Font.font("Arial", 20));
        exitButton.setOnAction(e -> {Platform.exit();}); // Collega il pulsante al metodo
        layout.getChildren().addAll(title, exitButton, menuButton);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxWidth(450);
        layout.setLayoutX(Menu.LARGHEZZA_AREA_GIOCO/2-layout.getMaxWidth()/2);
        layout.setMaxHeight(200);
        layout.setLayoutY(Menu.ALTEZZA_AREA_GIOCO/2-layout.getMaxHeight()/2);
		areaGioco.getChildren().add(sfondo);
		areaGioco.getChildren().add(layout);
	}
}
