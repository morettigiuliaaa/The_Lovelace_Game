/*
 * per usare questa classe nel codice è sufficiente creare un'istanza di questa usando 
 * uno dei costruttori in base alle proprie esigenze.
 * per creare un'istanza basta scrivere: new FadeIn(nomeDelGruppo, tempoDiEsecuzione)
 */

package it.edu.iisgubbio.lovelace.dynamicEffects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class FadeIn {

	private Group group;
	private int time;
	private int step;

	private double opacity;

	Timeline timeline;

	/**
	 * Dynamic effect constructor, with this constructor the step are 20 per seconds
	 * @param g the group to apply the effect
	 * @param t in how much time (ms)
	 */
	public FadeIn(Group g, int t){
		group=g;
		time=t;
		step=20*time/1000; //make 20 step for each seconds
	}

	/**
	 * Dynamic effect constructor
	 * @param g the group to apply the effect
	 * @param t in how much time (ms)
	 * @param s the number of time the opacity is increased for each seconds. A higher value means a higher quality.
	 */
	public FadeIn(Group g, int t, int s){
		group=g;
		time=t;
		step=s*time/1000;
	}
	public void start() {
		int timelineTime=time/step;
		//this control avoid the creation of more than one instance of the class "Timeline"
		if(timeline==null) {
			timeline= new Timeline(new KeyFrame(
					Duration.millis(timelineTime),
					x -> action()));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}
	}
	private void action() {
		opacity+=1.0/step;
		if(opacity<1.05) {
//			System.out.println(opacity);
			group.setOpacity(opacity);
		}else{
			opacity=0;
			timeline.stop();
			timeline=null;
		}
	}
}
