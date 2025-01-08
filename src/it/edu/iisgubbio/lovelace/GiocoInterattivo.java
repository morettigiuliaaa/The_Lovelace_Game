package it.edu.iisgubbio.lovelace;

import it.edu.iisgubbio.lovelace.dynamicEffects.FadeOut;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GiocoInterattivo {
	GameOver gameover;

	private Scene scena;
	private Pane areaGioco = new Pane();
	private Timeline timelineGioco;

	private Rectangle rettangolo = new Rectangle(250, 400);
	private Rectangle rettangoloAda = new Rectangle(40, 413);
	private Image adaImageDx = new Image(getClass().getResourceAsStream("ada_dx.png"));
	private Image adaImageSx = new Image(getClass().getResourceAsStream("ada_sx.png"));
	private ImageView adaFerma = new ImageView(adaImageSx); // Immagine ferma

	private int frameIndex = 0; // Indice frame corrente
	private static final String[] FRAMES_SX = { 
			GiocoInterattivo.class.getResource("AdaFrame1.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame2.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame3.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame4.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame5.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame6.png").toExternalForm() };
	private static final String[] FRAMES_DX = { 
			GiocoInterattivo.class.getResource("AdaFrame1_dx.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame2_dx.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame3_dx.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame4_dx.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame5_dx.png").toExternalForm(),
			GiocoInterattivo.class.getResource("AdaFrame6_dx.png").toExternalForm() };

	private ImageView adaImageView = adaFerma;
	int puntoInizioCorpoAda;
	private Timeline movimentoTimeline; // Timeline per l'animazione

	public GiocoInterattivo(Scene scenaPrimaria, RaccoltaTesti testoDialogo) {
		// Inizializzazione
		OggettoPannello.setTestoDialogo(testoDialogo);
		scena = scenaPrimaria;
		scena.setOnKeyPressed(e -> pigiato(e)); // Gestisce la pressione del tasto
		scena.setOnKeyReleased(e -> rilasciato(e)); // Gestisce la pressione del tasto
		areaGioco = (Pane) scena.getRoot();
		Menu.finestra.setScene(scena);
		timelineGioco = new Timeline(new KeyFrame(Duration.millis(100), x -> cambioScena(testoDialogo)));
		timelineGioco.setCycleCount(Animation.INDEFINITE);
		timelineGioco.play();
	}

	// Metodo per il movimento in avanti (animazione)
	public void movimentoAdaAvanti(Pane areaGioco) {
		if (adaImageView == null) {
			adaImageView = new ImageView(new Image(FRAMES_SX[0]));
			areaGioco.getChildren().add(adaImageView);
		}
		// Avvio dell'animazione in avanti
		movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
			frameIndex = (frameIndex + 1) % FRAMES_SX.length; // Incrementa in modo ciclico
			// limitiamo lo spostamento
			if (adaFerma.getX() < Menu.LARGHEZZA_AREA_GIOCO - (puntoInizioCorpoAda + 50)) {
				adaImageView.setImage(new Image(FRAMES_SX[frameIndex]));
				if (avanti) {
					adaFerma.setX(adaFerma.getX() + 20.0);
					rettangoloAda.setX(adaFerma.getX() + puntoInizioCorpoAda + (adaFerma.getFitWidth() / 100) * 27);
				} else {
					adaImageView=adaFerma;
				}
			}
		}));
		movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
		movimentoTimeline.play();
	}
	
	boolean movimentoDietro=false;
	// Metodo per il movimento indietro (animazione)
	public void movimentoAdaDietro(Pane areaGioco) {
		movimentoDietro=true;
		if (adaImageView == null) {
			adaImageView = new ImageView(new Image(FRAMES_DX[5])); // Inizia dall'ultimo frame
			areaGioco.getChildren().add(adaImageView);
		}

		// Avvio dell'animazione indietro
		movimentoTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
			frameIndex = (frameIndex - 1 + FRAMES_DX.length) % FRAMES_DX.length; // Decrementa in modo ciclico
			// limitiamo lo spostamento
			if (adaFerma.getX() > -40 - puntoInizioCorpoAda) {
				adaImageView.setImage(new Image(FRAMES_DX[frameIndex]));
				adaFerma.setX(adaFerma.getX() - 20.0);
				rettangoloAda.setX(adaFerma.getX() + puntoInizioCorpoAda + (adaFerma.getFitWidth() / 100) * 27);
			} else {
				adaImageView=adaFerma;
			}
		}));

		movimentoTimeline.setCycleCount(Timeline.INDEFINITE);
		movimentoTimeline.play();
	}

	// Gestisce la pressione dei tasti
	long tempoSalto = 0;

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
			if (System.currentTimeMillis() - tempoSalto > 100) { // puoi ripetere il salto
				if(areaGioco.getId().equals("gioco2") && Utilita.collisioniRettangoliScenaDue(mDiRettangoli[0], rettangoloAda) != 0) {
					saltoAda();
				}else if(areaGioco.getId().equals("gioco3") && Utilita.collisioniRettangoliScenaDue(mDiRettangoli[1], rettangoloAda) != 0){
					saltoAda();
				}
			}

		}
	}

	private void rilasciato(KeyEvent evento) {
		if (evento.getCode() == KeyCode.A || evento.getCode() == KeyCode.LEFT || evento.getCode() == KeyCode.D
				|| evento.getCode() == KeyCode.RIGHT) {
			if (movimentoTimeline != null) {
				movimentoTimeline.stop();
				movimentoTimeline = null;
				
				if (movimentoDietro) {
					adaImageView.setImage(adaImageDx);
					movimentoDietro=false;
				}else{
					adaImageView.setImage(adaImageSx);
				}
				
			}
		}
	}

	Timeline saltoTimeline;
	/**
	 * @param posizioneDaRaggiungere in che altezza il salto si deve fermare.
	 */
	private void saltoAda() {
		if (saltoTimeline == null && !areaGioco.getId().equals("gioco1")) {
			double altezzaSalto = 220.0;
			saltoTimeline = new Timeline(
					// Salita
					new KeyFrame(Duration.millis(9), event -> {
						adaFerma.setY(adaFerma.getY() - altezzaSalto / 50);
						rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 20);
					}));

			saltoTimeline.setCycleCount(50); // Esegui solo una volta
			saltoTimeline.play();
			saltoTimeline.setOnFinished(e -> {
//				saltoTimeline.getKeyFrames().clear();
//				saltoTimeline.getKeyFrames().add(
//						// scendi
//						new KeyFrame(Duration.millis(10), event -> {
//							if (adaFerma.getY() < (posizioneDaRaggiungere + valoreOffset())) {
//								adaFerma.setY(adaFerma.getY() + altezzaSalto / 50);
//								rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 20);
//							} else {
				tempoSalto = System.currentTimeMillis(); // salviamo quando è finito il salto
//								saltoTimeline.stop();
				saltoTimeline = null;
//							}
//						}));
			});
		}
	}

	/**
	 * metodo che in base alla posizione di ada e la scena attuale, gestisce che
	 * offset restituire
	 * 
	 * @return di quanto deve essere l'offset
	 */
//	private int valoreOffset() {
//		double adaPosizioneX = adaImageView.getX() + puntoInizioCorpoAda;
//		if (areaGioco.getId().equals("gioco2")) {
//			// i controlli delle posizioni devono essere eseguiti da quello con una
//			// posizione più alta a quello più piccolo
//			// per salire il valore deve essere negativo per scendere positivo
//			if (adaPosizioneX >= 600) {
//				return -170;
//			} else if (adaPosizioneX >= 520) {
//				return -140;
//			} else if (adaPosizioneX >= 430) {
//				return -100;
//			} else if (adaPosizioneX >= 380) {
//				return -40;
//			} else if (adaPosizioneX >= 300) {
//				return 5;
//			} else if (adaPosizioneX >= 170) {
//				return 200;
//			}
//		}
//		return 0;
//	}

	// Metodo per cambiare scena (continuazione logica del gioco)
	private void cambioScena(RaccoltaTesti testoDialogo) {
		int nElemnti = areaGioco.getChildren().size();
		Group gruppo = (Group) areaGioco.getChildren().get(nElemnti - 1);
		if (gruppo.getOpacity() >= 1) {
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			time = System.currentTimeMillis();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(50), x -> scenaUno(testoDialogo, gruppo)));
			timelineGioco.play();
			gioco(gruppo);
		}
	}

	public void scenaUno(RaccoltaTesti testoDialogo, Group gruppo) {
		Shape intersezUno = Shape.intersect(rettangolo, rettangoloAda);
		if (intersezUno.getBoundsInLocal().getWidth() != -1) {
			scenaDue(testoDialogo, gruppo);
		}
	}

	Rectangle testaAda = new Rectangle(25, 30);
	Rectangle lava = new Rectangle(250, 60);

	Rectangle chest1 = Utilita.rettangolo(200, 260, 100, 110);
	Rectangle chest2 = Utilita.rettangolo(460, 100, 100, 120);
	Rectangle rettangoloPorta = Utilita.rettangolo(730, 150, 120, 270);

	boolean avanti = true;
	Domande domanda=null;
	int nDomanda = 1;
	long time;
	long timeDomande; // tempo in millis nel quale è stata fatta la domanda
	Pane schermataGioco = new Pane(); // variabile di appoggio per areaGioco
	OggettoPannello dialogo; // per un dialogo
	Group dialogoView; // per visualizzare un dialogo
	Rectangle mDiRettangoli[][] = { 
		{ 	Utilita.rettangolo(6.5, 575, 190, 20), 
			Utilita.rettangolo(335.5, 580, 90, 5),
			Utilita.rettangolo(413, 530, 90, 70), 
			Utilita.rettangolo(476, 470, 90, 70),
			Utilita.rettangolo(556, 430, 90, 70), 
			Utilita.rettangolo(636, 400, 280, 70),
			Utilita.rettangolo(170, 355, 170, 20), 
			Utilita.rettangolo(415, 180, 180, 30) 
		}, {
			Utilita.rettangolo(0, 420, 175, 20), 
			Utilita.rettangolo(240, 505, 130, 10),
			Utilita.rettangolo(425, 470, 110, 10),
			Utilita.rettangolo(565, 400, 110, 10),
			Utilita.rettangolo(700, 400, 300, 30),
		} 
	};

	public void scenaDue(RaccoltaTesti testoDialogo, Group gruppo) {
		testaAda.setX(rettangoloAda.getX() - 10);
		testaAda.setY(rettangoloAda.getY() - adaFerma.getFitHeight() + 27);
		testaAda.setFill(Color.BLUE);
		lava.setFill(Color.rgb(1, 0, 0, 0.5));
		lava.setX(150);
		lava.setY(600);
		// System.out.println(rettangoloAda.getX()+" "+rettangoloAda.getY());
		int nOggettiCollisione = Utilita.collisioniRettangoliScenaDue(mDiRettangoli[0], rettangoloAda);
		if (nOggettiCollisione > 1) {
			avanti = false;
		} else {
			avanti = true;
		}
		if (Utilita.collisioniTetto(mDiRettangoli[0], testaAda)) {
			if (saltoTimeline != null) {
				tempoSalto = System.currentTimeMillis();
				saltoTimeline.stop();
				saltoTimeline = null;
			}
		}
		if (nOggettiCollisione == 0 && saltoTimeline == null) {
			adaFerma.setY(adaFerma.getY() + 210/50);
			rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 25);
		}
		if (Utilita.collisioniLava(lava, testaAda)) {
			domanda = null;
			gameover = new GameOver(areaGioco, testoDialogo, gruppo);
		}
		if (Utilita.collisioniPorta(rettangoloPorta, rettangoloAda)) {
			if(nDomanda>2) {
				scenaTre(testoDialogo, gruppo);
			}else {
				if(dialogo==null) {
					adaFerma.setX(40);
					rettangoloAda.setX(adaFerma.getX() + puntoInizioCorpoAda);
					double nC=(int)(Math.random()*2);
					if(nC==1) {
						dialogo = new OggettoPannello("notificaUscita1", adaImageSx, scena);
					}else {
						dialogo = new OggettoPannello("notificaUscita2", adaImageSx, scena);
					}
					dialogoView=dialogo.getFinestra();
					areaGioco.getChildren().add(dialogoView);
				}
			}
		}else {
			if(dialogo!=null && dialogo.statoDiscorso() && dialogo.tempoFine>1000 || OggettoPannello.manuale) {
				System.out.println("rimosso");
				areaGioco.getChildren().remove(dialogoView);
				scena.setOnKeyPressed(e -> pigiato(e));
				dialogo=null;
				OggettoPannello.manuale=false;
			}
		}

		if (!areaGioco.getId().equals("gioco2") && !areaGioco.getId().equals("gioco3")) {
			movimentoDietro=false;
			nDomanda=1;
			areaGioco.getChildren().clear();
			areaGioco.getChildren().add(gruppo);
			(new FadeOut(gruppo, 1000)).start();
			areaGioco.getChildren().addAll(mDiRettangoli[0]);
			areaGioco.getChildren().addAll(testaAda, lava, chest1, chest2, rettangoloPorta);
			areaGioco.setId("gioco2");
			adaImageView.setFitWidth(150);
			adaImageView.setFitHeight(150);
			adaFerma.setFitWidth(150);
			adaFerma.setFitHeight(150);
			puntoInizioCorpoAda = 36;
			adaImageView.setX(40);
			adaImageView.setY(440);
			testaAda.setVisible(false);
			lava.setVisible(false);
			// TODO:
			// rettangoli sotto le chest
			// quadrati per la chest
			// resize rettangolo ada
			// rettangolo porta
			// sistemazione immagine scena due abbassare la chest
			// set x rettangolo ada
			// posizionamento rettangolo collisione
			// quando si fa l'if del bound richiamare la domanda, a apartire dalla 1
			rettangoloAda.setFill(Color.ALICEBLUE);
			rettangoloAda.setWidth(30);
			rettangoloAda.setHeight(10);
			rettangoloAda.setX(adaFerma.getX() + puntoInizioCorpoAda);
			rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 20);
			rettangoloAda.setVisible(false);
			areaGioco.getChildren().add(rettangoloAda);
			areaGioco.getChildren().add(adaImageView);
			areaGioco.getChildren().remove(gruppo);
			chest1.setY(250);
			chest2.setY(100);
			
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(10), x -> scenaDue(testoDialogo, gruppo)));
			timelineGioco.play();
		}
//			//per far comparire un unico dialogo
//			int nDialogo=1;
//			dialogo = new OggettoPannello(nDialogo);
//			dialogoView=dialogo.getFinestra();
//			areaGioco.getChildren().add(dialogoView);
//		}
//		//per rimuovere il dialogo una volta conluso
//		if(dialogo!=null && dialogo.statoDiscorso()) {
//			System.out.println("rimosso");
//			areaGioco.getChildren().remove(dialogoView);
//			dialogo=null;
//		}

		int nCollisioneChest=Utilita.collisioneChest(chest1, rettangoloAda, chest2);
		if (nCollisioneChest>0) {
			if (domanda == null) {
				// creiamo una copia di areaGioco
				eseguiDomanda(testoDialogo);
			} else {
				int statoDomanda=domanda.getRispostaGiusta();
				// controlliamo lo stato della risposta
				if (statoDomanda==1) {
					nDomanda++;
					System.out.println(nDomanda);
					domanda = null;
					areaGioco.getChildren().clear();
	    			System.out.println("ritorno a gioco");
//	    			System.out.println(schermataGioco.getChildren());
					if (nCollisioneChest== 1) {
						chest1.setY(-200);
						areaGioco.getChildren().remove(chest1);
					} else if (nCollisioneChest== 2) {
						chest2.setY(-200);
						areaGioco.getChildren().remove(chest2);
					}
					int dimensionePane=schermataGioco.getChildren().size();
					for (int i = dimensionePane-1; i >= 0; i--) {
						Node primo=schermataGioco.getChildren().get(i);
						areaGioco.getChildren().add(primo);
					}
//	    			System.out.println(areaGioco.getChildren());
				}else if(System.currentTimeMillis() - timeDomande > 30000 || statoDomanda==0){
					adaImageView.setX(10);
					chest2.setY(-200);
					chest1.setY(-200);
					domanda=null;
					gameover = new GameOver(areaGioco, testoDialogo, gruppo);
				}
			}
		}
	}
	
	private void eseguiDomanda(RaccoltaTesti testoDialogo) {
		int dimensionePane=areaGioco.getChildren().size();
		for (int i = dimensionePane-1; i >= 0; i--) {
			Node primo=areaGioco.getChildren().getFirst();
			schermataGioco.getChildren().add(primo);
		}
		switch (nDomanda) {
		case 1:
			domanda = domanda1(testoDialogo);
			break;
		case 2:
			domanda = domanda2(testoDialogo);
			break;
		case 3:
			domanda = domanda3(testoDialogo);
			break;
		case 4:
			domanda = domanda4(testoDialogo);
			break;
		default:
			// finite le domande è finito anche il gioco
			System.out.println("completato");
			completato = true;
			timelineGioco.stop();
		}
		timeDomande = System.currentTimeMillis();
	}
	
	public void scenaTre(RaccoltaTesti testoDialogo, Group gruppo) {
		int nOggettiCollisione = Utilita.collisioniRettangoliScenaTre(mDiRettangoli[1], rettangoloAda);
		if (nOggettiCollisione > 1) {
			System.out.println("piu di una");
			avanti = false;
		} else {
			avanti = true;
		}
		if (nOggettiCollisione == 0 && saltoTimeline == null) {
			adaFerma.setY(adaFerma.getY() + 210/50);
			rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 25);
		}
		if (Utilita.collisioniLava(lava, testaAda)) {
			domanda = null;
			gameover = new GameOver(areaGioco, testoDialogo, gruppo);
		}
		if (Utilita.collisioniPorta(rettangoloPorta, rettangoloAda)) {
			if (domanda == null) {
				System.out.println("primo if");

				// creiamo una copia di areaGioco
				eseguiDomanda(testoDialogo);
			} else {
				// controlliamo lo stato della risposta

				int statoDomanda=domanda.getRispostaGiusta();
				if (statoDomanda==1) {
					System.out.println("secondo if");
					nDomanda++;
					domanda = null;
					areaGioco.getChildren().clear();
	    			System.out.println("ritorno a gioco");
//	    			System.out.println(schermataGioco.getChildren());
					int dimensionePane=schermataGioco.getChildren().size();
					for (int i = dimensionePane-1; i >= 0; i--) {
						Node primo=schermataGioco.getChildren().get(i);
						areaGioco.getChildren().add(primo);
					}
				}else if(System.currentTimeMillis() - timeDomande > 30000 || statoDomanda==0){
					domanda = null;
					rettangoloPorta.setY(-500);
					gameover = new GameOver(areaGioco, testoDialogo, gruppo);
					areaGioco.getChildren().remove(rettangoloPorta);

				}
			}
		}
		testaAda.setX(rettangoloAda.getX() - 10);
		testaAda.setY(rettangoloAda.getY() - adaFerma.getFitHeight() + 27);
		if (!areaGioco.getId().equals("gioco3")) {
			movimentoDietro=true;
			nDomanda=3;
			domanda = null;
//			System.out.println("sono nellif");
			areaGioco.getChildren().clear();
			areaGioco.getChildren().add(gruppo);
			(new FadeOut(gruppo, 1000)).start();
			areaGioco.setId("gioco3");
			adaImageView.setFitWidth(150);
			adaImageView.setFitHeight(150);
			adaFerma.setFitWidth(150);
			adaFerma.setFitHeight(150);
			adaFerma.setImage(adaImageDx);
			puntoInizioCorpoAda = 36;
			adaImageView.setX(Menu.LARGHEZZA_AREA_GIOCO-180);
			adaImageView.setY(400-adaFerma.getFitHeight());
			rettangoloAda.setFill(Color.ALICEBLUE);
			rettangoloAda.setWidth(30);
			rettangoloAda.setHeight(10);
			rettangoloAda.setX(adaFerma.getX() + puntoInizioCorpoAda);
			rettangoloAda.setY(adaFerma.getY() + adaFerma.getFitHeight() - 20);
			rettangoloAda.setVisible(true);
			areaGioco.getChildren().add(rettangoloAda);
			areaGioco.getChildren().add(adaImageView);
			areaGioco.getChildren().remove(gruppo);
			
			lava.setY(Menu.ALTEZZA_AREA_GIOCO-40);
			lava.setX(0);
			lava.setWidth(Menu.LARGHEZZA_AREA_GIOCO);

			testaAda.setVisible(false);
			lava.setVisible(false);
			rettangoloAda.setVisible(false);
			
			rettangoloPorta.setX(20);
			rettangoloPorta.setY(175);
			areaGioco.getChildren().addAll(mDiRettangoli[1]);
			areaGioco.getChildren().addAll(rettangoloPorta, testaAda, lava);
			
			timelineGioco.stop();
			timelineGioco.getKeyFrames().clear();
			timelineGioco.getKeyFrames().add(new KeyFrame(Duration.millis(10), x -> scenaTre(testoDialogo, gruppo)));
			timelineGioco.play();
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
		areaGioco.getChildren().add(rettangolo);
		// le dimesnioni seguenti sono quelle originali delle foto
		adaImageView.setFitWidth(490);
		adaImageView.setFitHeight(490);
		adaFerma.setFitWidth(490);
		adaFerma.setFitHeight(490);
		puntoInizioCorpoAda = 106;
		adaFerma.setX(0);
		adaFerma.setY(100);
		// posizionamento rettangolo collisione
		rettangoloAda.setX(adaFerma.getX() + adaFerma.getFitWidth() - 220);
		rettangoloAda.setY(adaFerma.getY() + 40);
		rettangoloAda.setVisible(false);
		rettangolo.setY(125);
		rettangolo.setX(550);
		rettangolo.setVisible(false);
		areaGioco.getChildren().add(rettangoloAda);
		// adaImageView.toFront();
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
