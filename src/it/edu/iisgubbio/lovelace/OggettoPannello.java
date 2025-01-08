package it.edu.iisgubbio.lovelace;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class OggettoPannello {
    Group finestra = new Group();
    
    Image augustoImage = new Image(getClass().getResourceAsStream("augusto.png"));
    Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    
	AudioClip voceAda = new AudioClip(getClass().getResource("voceAda.mp3").toString());
	AudioClip voceAugusto = new AudioClip(getClass().getResource("voceAugusto.mp3").toString());
	AudioClip strappo = new AudioClip(getClass().getResource("strappo.mp3").toString());
    
	int indexDialoghi=0;
	
    //testi
    private DialogoImmagine[] dialogoImage={
    		new DialogoImmagine("dialogo1Ada", adaImage),
    		new DialogoImmagine("dialogo1Augusto", augustoImage),
    		new DialogoImmagine("Strappo", augustoImage),
    		new DialogoImmagine("dialogo2Ada", adaImage),
    		new DialogoImmagine("dialogo2Augusto", augustoImage),
    		new DialogoImmagine("dialogo3Augusto", augustoImage),
    		new DialogoImmagine("dialogo4Ada", adaImage),
    		new DialogoImmagine("finale1ada", adaImage),
    		new DialogoImmagine("finale1augusto", augustoImage),
    		new DialogoImmagine("finale2ada", adaImage),
    		new DialogoImmagine("finale2augusto", augustoImage),
    		new DialogoImmagine("finale3ada", adaImage),
    };
    
    private boolean stato; //indica se l'animazione del testo è finita o no
    
    static RaccoltaTesti testoDialogo;
    
    static public void setTestoDialogo(RaccoltaTesti testo) {
    	testoDialogo=testo;
    }
    
    /**
     * costruttore che attraverso il numero dato in ingresso prende il testo e l'immagine del vettore dialogoImage
     * @param i è l'indice del vettore che contiene il testo interessato
     */
	public OggettoPannello(int i){
		voceAda.setVolume(Menu.volume);
		voceAugusto.setVolume(Menu.volume);
		strappo.setVolume(Menu.volume);
		strappo.setCycleCount(1);
		voceAda.setCycleCount(Animation.INDEFINITE);
		voceAugusto.setCycleCount(Animation.INDEFINITE);
		voceAugusto.setCycleCount(Animation.INDEFINITE);
		indexDialoghi=i;
		oggettoPannello(testoDialogo.getString(dialogoImage[i].nomeDialogo), dialogoImage[i].immaginePersonaggio);
	}
	public OggettoPannello(String nomeTesto, Image personaggio){
		voceAda.setVolume(Menu.volume);
		voceAugusto.setVolume(Menu.volume);
		strappo.setVolume(Menu.volume);
		strappo.setCycleCount(1);
		voceAda.setCycleCount(Animation.INDEFINITE);
		voceAugusto.setCycleCount(Animation.INDEFINITE);
		voceAugusto.setCycleCount(Animation.INDEFINITE);
		oggettoPannello(testoDialogo.getString(nomeTesto), personaggio);
	}
	
	public void oggettoPannello(String testo, Image personaggio) {
        // Crea la finestra con lo sfondo
        Rectangle sfondo = new Rectangle(900, 300);
        sfondo.setFill(Color.rgb(0 , 0 , 0 , 0.7)); // Sfondo semitrasparente
        
        // Crea l'immagine del personaggio
        ImageView imgPersonaggio = new ImageView(personaggio);
        imgPersonaggio.setLayoutX(30);
        imgPersonaggio.setLayoutY(30);
        imgPersonaggio.setFitWidth(150);
        imgPersonaggio.setFitHeight(150);
        
        // Crea la TextArea per il dialogo
        TextArea dialogo = new TextArea();
        dialogo.setStyle("-fx-font-size: 18px;");
        dialogo.setPrefHeight(170); // Imposta un'altezza preferita
        dialogo.setPrefWidth(500);  // Imposta una larghezza preferita
        dialogo.setLayoutX(250);
        dialogo.setLayoutY(30);
        dialogo.setWrapText(true);
        dialogo.setEditable(false); // Impedisce la modifica diretta del testo
        
        // Aggiungi tutto alla finestra
        finestra.getChildren().add(sfondo);
        finestra.getChildren().add(dialogo);
        finestra.getChildren().add(imgPersonaggio);
        finestra.setLayoutY(450);
        
        // Chiamare la funzione per l'effetto di scrittura
        showTypingEffect(dialogo, testo);
        //setStatoDiscorso(true);
    }
	

	// Funzione per gestire l'effetto di scrittura
    private void showTypingEffect(TextArea dialogo, String testoDialogo) {
        Timeline timeline = new Timeline();
        final StringBuilder textBuilder = new StringBuilder();
        final int[] index = {0};  // Indice per tenere traccia della posizione nel testo
        if(dialogoImage[indexDialoghi].personaggio.equals("Ada")) {
        	voceAda.play();
        }else if(dialogoImage[indexDialoghi].personaggio.equals("Augusto")){
        	voceAugusto.play();
        }else if(dialogoImage[indexDialoghi].personaggio.equals("Strappo")){
        	strappo.play();
        }
        // Crea una KeyFrame per aggiungere un carattere ogni 100 millisecondi
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event -> {
            if (index[0] < testoDialogo.length()) {
                textBuilder.append(testoDialogo.charAt(index[0]));  // Aggiungi il carattere
                dialogo.setText(textBuilder.toString());             // Imposta il testo del dialogo
                index[0]++;  // Incrementa l'indice per il prossimo carattere
                stato=false;
            }else {
            	setStatoDiscorso(true);
            	stato=true;
            	voceAugusto.stop();
            	voceAda.stop();
            	strappo.stop();
            }
        });

        // Aggiungi la KeyFrame alla Timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(testoDialogo.length()+1);  // Si ferma quando tutto il testo è scritto
        timeline.setRate(1);  // Velocità dell'effetto
        timeline.play();  // Avvia l'animazione
    }
    
    /**
     * metodo che ritorna se l'animazione del testo è conclusa o no
     * @return lo stato dell'animazione true= conlcusa, false= in corso
     */
    public boolean statoDiscorso() {
    	return stato;
    }
    public void setStatoDiscorso(boolean stato) {
		this.stato = stato;
	}
    
    // Metodo per ottenere la finestra
    public Group getFinestra() {
        return finestra;
    }
}