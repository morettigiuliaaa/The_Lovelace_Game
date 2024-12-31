package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.*;
import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GiocoInterattivo {
    private boolean arrivatosù = false;
    private boolean arrivatogiù = true;

    private Scene scena;
    private Pane areaGioco = new Pane();
    private Timeline timelineGioco;

    private Image adaImage = new Image(getClass().getResourceAsStream("ada_sx.png"));
    private ImageView adaferma = new ImageView(adaImage); // Immagine ferma
    
    private int frameIndex = 0; // Indice frame corrente
    private static final String[] FRAMES = {
        GiocoInterattivo.class.getResource("AdaFrame1.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame2.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame3.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame4.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame5.png").toExternalForm(),
        GiocoInterattivo.class.getResource("AdaFrame6.png").toExternalForm()
    };

    private ImageView adaImageView = adaferma;
    private Timeline movimentoTimeline;  // Timeline per l'animazione
    private boolean isMoving = false;  // Indica se Ada è in movimento
    
    public GiocoInterattivo(Scene scenaPrimaria, RaccoltaTesti testoDialogo) {
        // Inizializzazione
        OggettoPannello.setTestoDialogo(testoDialogo);
        scena = scenaPrimaria;
    	scena.setOnKeyPressed(e -> pigiato(e));  // Gestisce la pressione del tasto
    	scena.setOnKeyReleased(e -> rilasciato(e));  // Gestisce la pressione del tasto
        areaGioco = (Pane) scena.getRoot();
        Menu.finestra.setScene(scena);
        timelineGioco = new Timeline(new KeyFrame(Duration.millis(100), x -> cambioScena(testoDialogo)));
        timelineGioco.setCycleCount(Animation.INDEFINITE);
        timelineGioco.play();
    }

    // Metodo per il movimento in avanti (animazione)
    public void movimentoAdaAvanti(Pane areaGioco) {
        if (adaImageView == null) {
            adaImageView = new ImageView(new Image(FRAMES[0]));
            adaImageView.setFitWidth(100);
            adaImageView.setFitHeight(150);
            areaGioco.getChildren().add(adaImageView);
        }

        // Avvio dell'animazione in avanti
        movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            frameIndex = (frameIndex + 1) % FRAMES.length;  // Incrementa in modo ciclico
            adaImageView.setImage(new Image(FRAMES[frameIndex]));
        }));

        movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
        movimentoTimeline.play();
        isMoving = true;  // Imposta stato in movimento
    }

    // Metodo per il movimento indietro (animazione)
    public void movimentoAdaDietro(Pane areaGioco) {
        if (adaImageView == null) {
            adaImageView = new ImageView(new Image(FRAMES[5])); // Inizia dall'ultimo frame
            adaImageView.setFitWidth(100);
            adaImageView.setFitHeight(150);
            areaGioco.getChildren().add(adaImageView);
        }

        // Avvio dell'animazione indietro
        movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            frameIndex = (frameIndex - 1 + FRAMES.length) % FRAMES.length;  // Decrementa in modo ciclico
            adaImageView.setImage(new Image(FRAMES[frameIndex]));
        }));

        movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
        movimentoTimeline.play();
        isMoving = true;  // Imposta stato in movimento
    }

    // Gestisce la pressione dei tasti
    private void pigiato(KeyEvent evento) {
        if ((evento.getCode() == KeyCode.D || evento.getCode() == KeyCode.RIGHT)) {
            double posizione = adaferma.getX();
            adaferma.setX(posizione + 10.0);
            if(movimentoTimeline==null) {
            	movimentoAdaAvanti(areaGioco); // Avvia animazione se non è già in movimento
            }
            
        }
        if ((evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT)) {
            double posizione = adaferma.getX();
            adaferma.setX(posizione - 10.0);
            if(movimentoTimeline==null) {
            	movimentoAdaDietro(areaGioco); // Avvia animazione se non è già in movimento
            }
        }
    }
    private void rilasciato(KeyEvent evento) {
    	if(movimentoTimeline!=null){
    		movimentoTimeline.stop();
    		movimentoTimeline=null;
    		adaImageView.setImage(adaImage);
    	}
        
    }
    
    // Metodo per cambiare scena (continuazione logica del gioco)
    private void cambioScena(RaccoltaTesti testoDialogo) {
        int nElemnti = areaGioco.getChildren().size();
        Group gruppo = (Group) areaGioco.getChildren().get(nElemnti - 1);
        if (gruppo.getOpacity() >= 1) {
            timelineGioco.stop();
            timelineGioco.getKeyFrames().clear();
            time = System.currentTimeMillis();
            timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> evoluzioneGioco(testoDialogo)));
            timelineGioco.play();
            gioco(gruppo);
        }
    }
    Domande domanda;
    int nDomanda=1;
    long time;
    long timeDomande; //tempo in millis nel quale è stata fatta la domanda
	Pane schermataGioco = new Pane(); //variabile di appoggio per areaGioco
    public void evoluzioneGioco(RaccoltaTesti testoDialogo) {
    	//come far comparire
    	if(adaImageView.getX()>50) { //TODO: da cambiare con l'avvenuta collisione
    		if(domanda==null) {
    			//creiamo una copia di areaGioco
    			for(int i=0; i<areaGioco.getChildren().size(); i++){
    				schermataGioco.getChildren().add(areaGioco.getChildren().get(i));
    			}
    			switch (nDomanda) {
				case 1: 
					domanda=domanda1(testoDialogo);
					break;
				case 2:
					domanda=domanda2(testoDialogo);
					break;
				case 3: 
					domanda=domanda3(testoDialogo);
					break;
				case 4: 
					domanda=domanda4(testoDialogo);
					break;
				default:
					//finite le domande è finito anche il gioco
		            System.out.println("completato");
		            completato = true;
		            timelineGioco.stop();
				}
    			timeDomande=System.currentTimeMillis();
    		}else {
    			//controlliamo lo stato della risposta
	    		if(domanda.isRispostaGiusta() || System.currentTimeMillis() - timeDomande > 30000) {
	    			nDomanda++;
	    			domanda=null;
	    			adaImageView.setX(10);
	    			areaGioco.getChildren().clear();
//	    			System.out.println("ritorno a gioco");
//	    			System.out.println(schermataGioco.getChildren());
	    			for(int i=0; i<schermataGioco.getChildren().size(); i++){
	    				areaGioco.getChildren().add(schermataGioco.getChildren().get(i));
	    			}
//	    			System.out.println(areaGioco.getChildren());
	    		}
    		}
    		
    	}
    }

    public void gioco(Group gruppo) {
        areaGioco.getChildren().clear();
        areaGioco.getChildren().add(adaImageView);
        cambioalgioco(gruppo);
    }
    public void cambioalgioco(Group gruppo) {
    	(new FadeOut(gruppo, 1000)).start();
        areaGioco.getChildren().add(gruppo);
    	areaGioco.setId("gioco1");
    	adaImageView.resize(400, 500);
    	adaImageView.setLayoutX(-70);
    	adaImageView.setLayoutY(85);
    	//adaImageView.toFront();
    }
    
    public Domande domanda1(RaccoltaTesti testoDialogo) {
    	Domande domande = new Domande(areaGioco);
        domande.mostraDomanda1(testoDialogo);
        return domande;
    }
    
    public Domande domanda2(RaccoltaTesti testoDialogo) {
    	Domande domande = new Domande(areaGioco);
        domande.mostraDomanda2(testoDialogo);
        return domande;
    }
    
    public Domande domanda3(RaccoltaTesti testoDialogo) {
    	Domande domande = new Domande(areaGioco);
        domande.mostraDomanda3(testoDialogo);
        return domande;
    }
    
    public Domande domanda4(RaccoltaTesti testoDialogo) {
    	Domande domande = new Domande(areaGioco);
        domande.mostraDomanda4(testoDialogo);
        return domande;
    }

    boolean completato = false;

    public boolean isCompletato() {
        return completato;
    }

}
