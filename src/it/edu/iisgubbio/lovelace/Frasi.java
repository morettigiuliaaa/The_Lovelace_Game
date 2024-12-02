package it.edu.iisgubbio.lovelace;

import java.util.Locale;
import java.util.ResourceBundle;

public class Frasi {
	private static ResourceBundle prendiFrasi;
	
	public static void main(String[] args) {
		prendiFrasi= ResourceBundle.getBundle("it.edu.iisgubbio.lovelace.messaggi", Locale.ITALY);
		System.out.println(prendiFrasi.getString("saluto"));
	}
}
