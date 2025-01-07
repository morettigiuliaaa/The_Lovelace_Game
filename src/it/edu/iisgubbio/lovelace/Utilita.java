package it.edu.iisgubbio.lovelace;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Utilita {
	
	public static Rectangle rettangolo(double x, double y, double w, double h) {
		Rectangle r = new Rectangle(w,h);
		r.setX(x);
		r.setY(y);
		Color z = new Color(1, 0, 0, 0.5);
		r.setFill( z );
		return r;
	}
	
	public static int collisioniRettangoli(Rectangle rettangolo[], Rectangle ada) {
		int nCollisioni=0;
		
		
		for (int i=0; i<rettangolo.length; i++) {
			Shape intersezUno = Shape.intersect(rettangolo[i], ada);
			if (intersezUno.getBoundsInLocal().getWidth() != -1) {
				nCollisioni++;
			}

			
		
			
			
		}
		
		
		
		
		return nCollisioni;
	}
}
