package it.edu.iisgubbio.lovelace;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Domande {
	private Pane areaGioco;

	// Costruttore che accetta il Pane comune
	public Domande(Pane areaGioco) {
		this.areaGioco = areaGioco;
	}

	public void mostraDomanda1(RaccoltaTesti testoDialogo) {
		// Svuota l'area di gioco
		areaGioco.getChildren().clear();

		// Rettangolo per lo sfondo
		Rectangle sfondo = new Rectangle(600, 200);
		sfondo.setFill(Color.rgb(0, 0, 0, 0.5)); // Nero con opacità 0.5
		sfondo.setLayoutX(100);
		sfondo.setLayoutY(100);

		// TextArea per la domanda
		TextArea domanda = new TextArea();
		domanda.setPrefWidth(580);
		domanda.setPrefHeight(100);
		domanda.setLayoutX(110); // Centrato rispetto al rettangolo
		domanda.setLayoutY(150);
		domanda.setWrapText(true);
		domanda.setEditable(false);
		domanda.setStyle("-fx-font-size: 16px; -fx-control-inner-background: #ffffff; -fx-text-fill: black;");
		domanda.setText(testoDialogo.getString("domanda1"));

		// Bottoni per le risposte
		Button risposta1 = new Button(testoDialogo.getString("1risposta1"));
		Button risposta2 = new Button(testoDialogo.getString("1risposta2"));
		Button risposta3 = new Button(testoDialogo.getString("1risposta3"));
		Button risposta4 = new Button(testoDialogo.getString("1risposta4"));

		// Imposta posizione e dimensioni dei bottoni
		risposta1.setLayoutX(125);
		risposta1.setLayoutY(340);
		risposta1.setId("bottonedomanda");
		risposta1.setPrefWidth(250);

		risposta2.setLayoutX(125);
		risposta2.setLayoutY(450);
		risposta2.setId("bottonedomanda");
		risposta2.setPrefWidth(250);

		risposta3.setLayoutX(425);
		risposta3.setLayoutY(340);
		risposta3.setId("bottonedomanda");
		risposta3.setPrefWidth(250);

		risposta4.setLayoutX(425);
		risposta4.setLayoutY(450);
		risposta4.setId("bottonedomanda");
		risposta4.setPrefWidth(250);

		// Aggiungi controllo delle risposte
		risposta1.setOnAction(e -> verificaRisposta1("A"));
		risposta2.setOnAction(e -> verificaRisposta1("B"));
		risposta3.setOnAction(e -> verificaRisposta1("C"));
		risposta4.setOnAction(e -> verificaRisposta1("D"));

		// Aggiungi tutto all'area di gioco
		areaGioco.getChildren().addAll(sfondo, domanda, risposta1, risposta2, risposta3, risposta4);
	}

	// Metodo per verificare la risposta
	private void verificaRisposta1(String risposta) {
		String rispostaCorretta = "B"; // La risposta corretta per questa domanda
		if (risposta.equals(rispostaCorretta)) {
			System.out.println("Risposta corretta!");
		} else {
			System.out.println("Risposta sbagliata. Ritenta!");
		}
	}


	public void mostraDomanda2(RaccoltaTesti testoDialogo) {
		// Svuota l'area di gioco
		areaGioco.getChildren().clear();

		// Rettangolo per lo sfondo
		Rectangle sfondo = new Rectangle(600, 200);
		sfondo.setFill(Color.rgb(0, 0, 0, 0.5)); // Nero con opacità 0.5
		sfondo.setLayoutX(100);
		sfondo.setLayoutY(100);

		// TextArea per la domanda
		TextArea domanda = new TextArea();
		domanda.setPrefWidth(580);
		domanda.setPrefHeight(100);
		domanda.setLayoutX(110); // Centrato rispetto al rettangolo
		domanda.setLayoutY(150);
		domanda.setWrapText(true);
		domanda.setEditable(false);
		domanda.setStyle("-fx-font-size: 16px; -fx-control-inner-background: #ffffff; -fx-text-fill: black;");
		domanda.setText(testoDialogo.getString("domanda2"));

		// Bottoni per le risposte
		Button risposta1 = new Button(testoDialogo.getString("2risposta1"));
		Button risposta2 = new Button(testoDialogo.getString("2risposta2"));
		Button risposta3 = new Button(testoDialogo.getString("2risposta3"));
		Button risposta4 = new Button(testoDialogo.getString("2risposta4"));

		// Imposta posizione e dimensioni dei bottoni
		risposta1.setLayoutX(125);
		risposta1.setLayoutY(340);
		risposta1.setId("bottonedomanda");
		risposta1.setPrefWidth(250);

		risposta2.setLayoutX(125);
		risposta2.setLayoutY(450);
		risposta2.setId("bottonedomanda");
		risposta2.setPrefWidth(250);

		risposta3.setLayoutX(425);
		risposta3.setLayoutY(340);
		risposta3.setId("bottonedomanda");
		risposta3.setPrefWidth(250);

		risposta4.setLayoutX(425);
		risposta4.setLayoutY(450);
		risposta4.setId("bottonedomanda");
		risposta4.setPrefWidth(250);

		// Aggiungi controllo delle risposte
		risposta1.setOnAction(e -> verificaRisposta2("A"));
		risposta2.setOnAction(e -> verificaRisposta2("B"));
		risposta3.setOnAction(e -> verificaRisposta2("C"));
		risposta4.setOnAction(e -> verificaRisposta2("D"));

		// Aggiungi tutto all'area di gioco
		areaGioco.getChildren().addAll(sfondo, domanda, risposta1, risposta2, risposta3, risposta4);
	}

	// Metodo per verificare la risposta
	private void verificaRisposta2(String risposta) {
		String rispostaCorretta = "B"; // La risposta corretta per questa domanda
		if (risposta.equals(rispostaCorretta)) {
			System.out.println("Risposta corretta!");
		} else {
			System.out.println("Risposta sbagliata. Ritenta!");
		}
	}
	
	public void mostraDomanda3(RaccoltaTesti testoDialogo) {
		// Svuota l'area di gioco
		areaGioco.getChildren().clear();

		// Rettangolo per lo sfondo
		Rectangle sfondo = new Rectangle(600, 200);
		sfondo.setFill(Color.rgb(0, 0, 0, 0.5)); // Nero con opacità 0.5
		sfondo.setLayoutX(100);
		sfondo.setLayoutY(100);

		// TextArea per la domanda
		TextArea domanda = new TextArea();
		domanda.setPrefWidth(580);
		domanda.setPrefHeight(100);
		domanda.setLayoutX(110); // Centrato rispetto al rettangolo
		domanda.setLayoutY(150);
		domanda.setWrapText(true);
		domanda.setEditable(false);
		domanda.setStyle("-fx-font-size: 16px; -fx-control-inner-background: #ffffff; -fx-text-fill: black;");
		domanda.setText(testoDialogo.getString("domanda3"));

		// Bottoni per le risposte
		Button risposta1 = new Button(testoDialogo.getString("3risposta1"));
		Button risposta2 = new Button(testoDialogo.getString("3risposta2"));
		Button risposta3 = new Button(testoDialogo.getString("3risposta3"));
		Button risposta4 = new Button(testoDialogo.getString("3risposta4"));

		// Imposta posizione e dimensioni dei bottoni
		risposta1.setLayoutX(125);
		risposta1.setLayoutY(340);
		risposta1.setId("bottonedomanda");
		risposta1.setPrefWidth(250);

		risposta2.setLayoutX(125);
		risposta2.setLayoutY(450);
		risposta2.setId("bottonedomanda");
		risposta2.setPrefWidth(250);

		risposta3.setLayoutX(425);
		risposta3.setLayoutY(340);
		risposta3.setId("bottonedomanda");
		risposta3.setPrefWidth(250);

		risposta4.setLayoutX(425);
		risposta4.setLayoutY(450);
		risposta4.setId("bottonedomanda");
		risposta4.setPrefWidth(250);

		// Aggiungi controllo delle risposte
		risposta1.setOnAction(e -> verificaRisposta3("A"));
		risposta2.setOnAction(e -> verificaRisposta3("B"));
		risposta3.setOnAction(e -> verificaRisposta3("C"));
		risposta4.setOnAction(e -> verificaRisposta3("D"));

		// Aggiungi tutto all'area di gioco
		areaGioco.getChildren().addAll(sfondo, domanda, risposta1, risposta2, risposta3, risposta4);
	}

	// Metodo per verificare la risposta
	private void verificaRisposta3(String risposta) {
		String rispostaCorretta = "B"; // La risposta corretta per questa domanda
		if (risposta.equals(rispostaCorretta)) {
			System.out.println("Risposta corretta!");
		} else {
			System.out.println("Risposta sbagliata. Ritenta!");
		}
	}


	public void mostraDomanda4(RaccoltaTesti testoDialogo) {
		// Svuota l'area di gioco
		areaGioco.getChildren().clear();

		// Rettangolo per lo sfondo
		Rectangle sfondo = new Rectangle(600, 200);
		sfondo.setFill(Color.rgb(0, 0, 0, 0.5)); // Nero con opacità 0.5
		sfondo.setLayoutX(100);
		sfondo.setLayoutY(100);

		// TextArea per la domanda
		TextArea domanda = new TextArea();
		domanda.setPrefWidth(580);
		domanda.setPrefHeight(100);
		domanda.setLayoutX(110); // Centrato rispetto al rettangolo
		domanda.setLayoutY(150);
		domanda.setWrapText(true);
		domanda.setEditable(false);
		domanda.setStyle("-fx-font-size: 16px; -fx-control-inner-background: #ffffff; -fx-text-fill: black;");
		domanda.setText(testoDialogo.getString("domanda4"));

		// Bottoni per le risposte
		Button risposta1 = new Button(testoDialogo.getString("4risposta1"));
		Button risposta2 = new Button(testoDialogo.getString("4risposta2"));
		Button risposta3 = new Button(testoDialogo.getString("4risposta3"));
		Button risposta4 = new Button(testoDialogo.getString("4risposta4"));

		// Imposta posizione e dimensioni dei bottoni
		risposta1.setLayoutX(125);
		risposta1.setLayoutY(340);
		risposta1.setId("bottonedomanda");
		risposta1.setPrefWidth(250);

		risposta2.setLayoutX(125);
		risposta2.setLayoutY(450);
		risposta2.setId("bottonedomanda");
		risposta2.setPrefWidth(250);

		risposta3.setLayoutX(425);
		risposta3.setLayoutY(340);
		risposta3.setId("bottonedomanda");
		risposta3.setPrefWidth(250);

		risposta4.setLayoutX(425);
		risposta4.setLayoutY(450);
		risposta4.setId("bottonedomanda");
		risposta4.setPrefWidth(250);

		// Aggiungi controllo delle risposte
		risposta1.setOnAction(e -> verificaRisposta4("A"));
		risposta2.setOnAction(e -> verificaRisposta4("B"));
		risposta3.setOnAction(e -> verificaRisposta4("C"));
		risposta4.setOnAction(e -> verificaRisposta4("D"));

		// Aggiungi tutto all'area di gioco
		areaGioco.getChildren().addAll(sfondo, domanda, risposta1, risposta2, risposta3, risposta4);
	}

	// Metodo per verificare la risposta
	private void verificaRisposta4(String risposta) {
		String rispostaCorretta = "B"; // La risposta corretta per questa domanda
		if (risposta.equals(rispostaCorretta)) {
			System.out.println("Risposta corretta!");
		} else {
			System.out.println("Risposta sbagliata. Ritenta!");
		}
	}
}
