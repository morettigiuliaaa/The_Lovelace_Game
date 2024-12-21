package it.edu.iisgubbio.lovelace;

import javafx.scene.image.Image;

public class DialogoImmagine {
	String nomeDialogo;
	Image immaginePersonaggio;
	String personaggio;
	
	public DialogoImmagine(String nomeDialogo, Image immaginePersonaggio) {
		this.immaginePersonaggio=immaginePersonaggio;
		this.nomeDialogo=nomeDialogo;
		//cerchiamo il nome del personaggio nel nome del dialogo
		if(nomeDialogo.indexOf("ada")!=-1) {
			personaggio="Ada";
		}else if(nomeDialogo.indexOf("augusto")!=-1){
			personaggio="Augusto";
		}else {
			personaggio=null;
		}
	}
}
