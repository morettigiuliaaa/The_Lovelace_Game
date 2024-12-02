package it.edu.iisgubbio.lovelace;

import java.util.Locale;
import java.util.ResourceBundle;

public class RaccoltaTesti {
	private ResourceBundle prendiFrasi;
	
	public RaccoltaTesti(Locale lingua){
		prendiFrasi= ResourceBundle.getBundle("it.edu.iisgubbio.lovelace.testi", lingua);
	}
	
	public String getString(String parolaChiave) {
		return prendiFrasi.getString(parolaChiave);
	}

	public void setLocale(Locale lingua) {
		this.prendiFrasi = ResourceBundle.getBundle("it.edu.iisgubbio.lovelace.testi", lingua);
	}
}
