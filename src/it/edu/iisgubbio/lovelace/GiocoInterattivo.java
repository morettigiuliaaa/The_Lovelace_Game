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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GiocoInterattivo {
	GameOver gameover;
	private boolean arrivatosù = false;
	private boolean arrivatogiù = true;

	private Scene scena;
	private Pane areaGioco = new Pane();
	private Timeline timelineGioco;

	private Rectangle rettangolo = new Rectangle(250, 400);
	private Rectangle rettangoloada = new Rectangle(30, 413);
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
	int puntoInizioCorpoAda;
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
			areaGioco.getChildren().add(adaImageView);
		}
		// Avvio dell'animazione in avanti
		movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
			frameIndex = (frameIndex + 1) % FRAMES.length;  // Incrementa in modo ciclico
			//limitiamo lo spostamento
			if(adaferma.getX()<Menu.LARGHEZZA_AREA_GIOCO-(puntoInizioCorpoAda+50)) {
				adaImageView.setImage(new Image(FRAMES[frameIndex]));
				adaferma.setX(adaferma.getX() + 20.0);
				rettangoloada.setX(adaferma.getX()+adaferma.getFitWidth()-220);
			}else {
				adaImageView.setImage(adaImage);
			}
		}));
		movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
		movimentoTimeline.play();
		isMoving = true;  // Imposta stato in movimento
	}

	// Metodo per il movimento indietro (animazione)
	public void movimentoAdaDietro(Pane areaGioco) {
		if (adaImageView == null) {
			adaImageView = new ImageView(new Image(FRAMES[5])); // Inizia dall'ultimo frame
			areaGioco.getChildren().add(adaImageView);
		}
		
		// Avvio dell'animazione indietro
		movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
			frameIndex = (frameIndex - 1 + FRAMES.length) % FRAMES.length;  // Decrementa in modo ciclico
			//limitiamo lo spostamento
			if(adaferma.getX()>-40-puntoInizioCorpoAda) {
				adaImageView.setImage(new Image(FRAMES[frameIndex]));
				adaferma.setX(adaferma.getX() - 20.0);
				rettangoloada.setX(adaferma.getX()+adaferma.getFitWidth()-220);
			}else {
				adaImageView.setImage(adaImage);
			}
		}));

		movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
		movimentoTimeline.play();
		isMoving = true;  // Imposta stato in movimento
	}

	// Gestisce la pressione dei tasti
	long tempoSalto=0;
	private void pigiato(KeyEvent evento) {
	    if (evento.getCode() == KeyCode.D || evento.getCode() == KeyCode.RIGHT) {
	        if (movimentoTimeline == null) {
	            movimentoAdaAvanti(areaGioco); // Avvia animazione se non è già in movimento
	        }
	    }
	    if (evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT) {
	        if (movimentoTimeline == null) {
	            movimentoAdaDietro(areaGioco); // Avvia animazione se non è già in movimento
	        }
	    }
	    if (evento.getCode() == KeyCode.W || evento.getCode() == KeyCode.UP) {
	    	if(System.currentTimeMillis()-tempoSalto>100) { //puoi ripetere il salto 1Sec dopo la fine del precedente
	    		if(areaGioco.getId().equals("gioco2")) {
	    			saltoAda(440); // Attiva il salto
	    		}else if(areaGioco.getId().equals("gioco1")) {
	    			saltoAda(140);
	    		}
	    			
	    	}
	        
	    }
	}

	private void rilasciato(KeyEvent evento) {
		if(evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT || evento.getCode() == KeyCode.D || evento.getCode() == KeyCode.RIGHT) {
			if(movimentoTimeline!=null){
				movimentoTimeline.stop();
				movimentoTimeline=null;
				adaImageView.setImage(adaImage);
			}
		}
	}
	
	Timeline saltoTimeline;
	/**
	 * @param posizioneDaRaggiungere in che altezza il salto si deve fermare.
	 */
	private void saltoAda(int posizioneDaRaggiungere) {
		if(saltoTimeline==null) {
			double altezzaSalto = 250.0;
			saltoTimeline = new Timeline(
					// Salita
					new KeyFrame(Duration.millis(9), event -> {
						System.out.println(adaferma.getY());
						adaferma.setY(adaferma.getY() - altezzaSalto/50);
						rettangoloada.setY(adaferma.getY()+40);
					})
			);
			saltoTimeline.setCycleCount(50); // Esegui solo una volta
			saltoTimeline.play();
			saltoTimeline.setOnFinished(e->{
				saltoTimeline.getKeyFrames().clear();
				saltoTimeline.getKeyFrames().add(
					//scendi
					new KeyFrame(Duration.millis(10), event -> {
						if(adaferma.getY()<(posizioneDaRaggiungere + valoreOffset())) {
							adaferma.setY(adaferma.getY() + altezzaSalto/50);
							rettangoloada.setY(adaferma.getY()+40);
						}else {
				    		tempoSalto=System.currentTimeMillis(); //salviamo quando è finito il salto
							saltoTimeline.stop();
							saltoTimeline=null;
						}
					})
				);
				saltoTimeline.play();
			});
		}
	}

	/**
	 * metodo che in base alla posizione di ada e la scena attuale, gestisce che offset restituire
	 * @return di quanto deve essere l'offset
	 */
	private int valoreOffset() {
		double adaPosizioneX=adaImageView.getX()+puntoInizioCorpoAda;
		if(areaGioco.getId().equals("gioco2")) {
			//i controlli delle posizioni devono essere eseguiti da quello con una posizione più alta a quello più piccolo
			//per salire il valore deve essere negativo per scendere positivo
			if(adaPosizioneX>=600){
				return -170;
			}else if(adaPosizioneX>=520){
				return -140;
			}else if(adaPosizioneX>=430){
				return -100;
			}else if(adaPosizioneX>=380){
				return -40;
			}else if(adaPosizioneX>=300) {
				return 5;
			}else if(adaPosizioneX>=170){
				return 200;
			}
		}
		return 0;
	}
	
	// Metodo per cambiare scena (continuazione logica del gioco)
	private void cambioScena(RaccoltaTesti testoDialogo) {
		int nElemnti = areaGioco.getChildren().size();
		Group gruppo = (Group) areaGioco.getChildren().get(nElemnti - 1);
		if (gruppo.getOpacity() >= 1) {
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			time = System.currentTimeMillis();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> scenauno(testoDialogo, gruppo)));
			timelineGioco.play();
			gioco(gruppo);
		}
	}
	
	public void scenauno(RaccoltaTesti testoDialogo, Group gruppo) {
		Shape intersezUno = Shape.intersect(rettangolo, rettangoloada);
		if (intersezUno.getBoundsInLocal().getWidth() != -1) {
			scenadue(testoDialogo, gruppo);
		}
	}
	
	Domande domanda;
	int nDomanda=1;
	long time;
	long timeDomande; //tempo in millis nel quale è stata fatta la domanda
	Pane schermataGioco = new Pane(); //variabile di appoggio per areaGioco
	OggettoPannello dialogo; //per un dialogo
	Group dialogoView; //per visualizzare un dialogo
	public void scenadue(RaccoltaTesti testoDialogo, Group gruppo) {
		if(!areaGioco.getId().equals("gioco2")) {
			areaGioco.getChildren().clear();
			areaGioco.getChildren().add(gruppo);
			(new FadeOut(gruppo, 1000)).start();
			for(int i=0; i<Menu.LARGHEZZA_AREA_GIOCO; i+=25) {
				areaGioco.getChildren().add(new Line(i,0,i,Menu.ALTEZZA_AREA_GIOCO));
			}
			areaGioco.setId("gioco2");
			adaImageView.setFitWidth(150);
			adaImageView.setFitHeight(150);
			adaferma.setFitWidth(150);
			adaferma.setFitHeight(150);
			puntoInizioCorpoAda=36;
			adaImageView.setX(40);
			adaImageView.setY(440);
			// TODO:
			// rettangoli sotto le chest 
			// quadrati per la chest
			// resize rettangolo ada
			// rettangolo porta
			// sistemazione immagine scena due abbassare la chest
			// set x rettangolo ada
			//posizionamento rettangolo collisione
			// quando si fa l'if del bound richiamare la domanda, a apartire dalla 1
			rettangoloada.setX(adaferma.getX()+adaferma.getFitWidth()-220);
			rettangoloada.setY(adaferma.getY()+40);
			rettangoloada.setVisible(true);
			areaGioco.getChildren().add(rettangoloada);
			areaGioco.getChildren().add(adaImageView);
			areaGioco.getChildren().remove(gruppo);
		}
//			//per far comparire un unico dialogo
//			int nDialogo=1;
//			dialogo = new OggettoPannello(nDialogo);
//			dialogoView=dialogo.getFinestra();
//			areaGioco.getChildren().add(dialogoView);
//			
//			timelineGioco.stop();
//			timelineGioco.getKeyFrames().clear();
//			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> scenadue(testoDialogo, gruppo)));
//			timelineGioco.play();
//		}
//		//per rimuovere il dialogo una volta conluso
//		if(dialogo!=null && dialogo.statoDiscorso()) {
//			System.out.println("rimosso");
//			areaGioco.getChildren().remove(dialogoView);
//			dialogo=null;
//		}
		
//		//come far comparire
//    	if(adaImageView.getX()>50) { //TODO: da cambiare con l'avvenuta collisione
//    		if(domanda==null) {
//    			//creiamo una copia di areaGioco
//    			for(int i=0; i<areaGioco.getChildren().size(); i++){
//    				schermataGioco.getChildren().add(areaGioco.getChildren().get(i));
//    			}
//    			switch (nDomanda) {
//				case 1: 
//					domanda=domanda1(testoDialogo);
//					break;
//				case 2:
//					domanda=domanda2(testoDialogo);
//					break;
//				case 3: 
//					domanda=domanda3(testoDialogo);
//					break;
//				case 4: 
//					domanda=domanda4(testoDialogo);
//					break;
//				default:
//					//finite le domande è finito anche il gioco
//		            System.out.println("completato");
//		            completato = true;
//		            timelineGioco.stop();
//				}
//    			timeDomande=System.currentTimeMillis();
//    		}else {
//    			//controlliamo lo stato della risposta
//	    		if(domanda.isRispostaGiusta() || System.currentTimeMillis() - timeDomande > 30000) {
//	    			nDomanda++;
//	    			domanda=null;
//	    			adaImageView.setX(10);
//	    			areaGioco.getChildren().clear();
////	    			System.out.println("ritorno a gioco");
////	    			System.out.println(schermataGioco.getChildren());
//	    			for(int i=0; i<schermataGioco.getChildren().size(); i++){
//	    				areaGioco.getChildren().add(schermataGioco.getChildren().get(i));
//	    			}
////	    			System.out.println(areaGioco.getChildren());
//	    		}
//    		}
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
		areaGioco.getChildren().add(rettangolo);
		//le dimesnioni seguenti sono quelle originali delle foto
		adaImageView.setFitWidth(490);
		adaImageView.setFitHeight(490);
		adaferma.setFitWidth(490);
		adaferma.setFitHeight(490);
		puntoInizioCorpoAda=106;
		adaferma.setX(0);
		adaferma.setY(100);
		//posizionamento rettangolo collisione
		rettangoloada.setX(adaferma.getX()+adaferma.getFitWidth()-220);
		rettangoloada.setY(adaferma.getY()+40);
		rettangoloada.setVisible(false);
		rettangolo.setY(125);
		rettangolo.setX(550);
		rettangolo.setVisible(false);
		areaGioco.getChildren().add(rettangoloada);
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
