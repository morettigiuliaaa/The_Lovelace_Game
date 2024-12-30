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
		if(nomeDialogo.indexOf("Ada")!=-1) {
			personaggio="Ada";
		}else if(nomeDialogo.indexOf("Augusto")!=-1){
			personaggio="Augusto";
		}else if(nomeDialogo.indexOf("Strappo")!=-1){
			personaggio="Strappo";
		}
	}
}
