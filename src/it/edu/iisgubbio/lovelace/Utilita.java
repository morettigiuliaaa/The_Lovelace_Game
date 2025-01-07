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
	
	
	public static boolean collisioniTetto(Rectangle rettangolo[], Rectangle testaAda) {
		Shape intersezUno = Shape.intersect(rettangolo[6], testaAda);
		Shape intersezDue = Shape.intersect(rettangolo[7], testaAda);
		
		return (intersezUno.getBoundsInLocal().getWidth() != -1 || intersezDue.getBoundsInLocal().getWidth() != -1 || testaAda.getY()<=0 );
	}
	
	public static int collisioneChest(Rectangle chest1, Rectangle rettangoloAda, Rectangle chest2) {
		Shape intersezUno = Shape.intersect(chest1, rettangoloAda);
		Shape intersezDue = Shape.intersect(chest2, rettangoloAda);
		int nChest=0;
		if(intersezUno.getBoundsInLocal().getWidth() != -1 ) {
			nChest=1;
		}else if(intersezDue.getBoundsInLocal().getWidth() != -1 ){
			nChest=2;
		}
		
		return nChest;
	}
	
	
	public static boolean collisioniLava(Rectangle lava, Rectangle testaAda) {
		Shape intersezUno = Shape.intersect(lava, testaAda);
		
		return (intersezUno.getBoundsInLocal().getWidth() != -1) ;  
	}
	
	public static boolean collisioniPorta(Rectangle porta, Rectangle rettangoloAda) {
		Shape intersezUno = Shape.intersect(porta, rettangoloAda);
		
		return (intersezUno.getBoundsInLocal().getWidth() != -1) ;  
	}
	public static int collisioniRettangoliScenaDue(Rectangle rettangolo[], Rectangle ada) {
		int nCollisioni=0;
		for (int i=0; i<rettangolo.length; i++) {
			Shape intersezUno = Shape.intersect(rettangolo[i], ada);
//			System.out.println(intersezUno.getBoundsInLocal().getWidth());
			if (intersezUno.getBoundsInLocal().getWidth() != -1) {
				//System.out.println("collide");
				nCollisioni++;
			}
		}
		return nCollisioni;
	}
	public static int collisioniRettangoliScenaTre(Rectangle rettangolo[], Rectangle ada) {
		int nCollisioni=0;
		for (int i=0; i<rettangolo.length; i++) {
			Shape intersezUno = Shape.intersect(rettangolo[i], ada);
//			System.out.println(intersezUno.getBoundsInLocal().getWidth());
			if (intersezUno.getBoundsInLocal().getWidth() != -1) {
				//System.out.println("collide");
				nCollisioni++;
			}
		}
		return nCollisioni;
	}
}
