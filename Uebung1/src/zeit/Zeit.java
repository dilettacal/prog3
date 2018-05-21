package zeit;
import java.util.Calendar;
/**
 * @author Diletta Cal
 * 08.10.2017
 * @version
 *
 */
public class Zeit {
	
	private int stunden, minuten, sekunden;
	
	/**
	 * Standard-Konstruktor weist den Attributen die aktuelle Uhrzeit im Betriebssysteme zu.
	 */
	public Zeit(){
		Calendar now = Calendar.getInstance();
		stunden = now.get(Calendar.HOUR_OF_DAY);
		minuten = now.get(Calendar.MINUTE);
		sekunden = now.get(Calendar.SECOND);
		
	}
	
	/**
	 * Parameter-Konstruktor erzeugt ein Zeit-Objekt
	 * @param stunden die Stunden
	 * @param minuten die Minuten
	 * @param sekunden die Sekunden
	 */
	public Zeit(int stunden, int minuten, int sekunden){
		Calendar reset = null;
		//Falls Parameter negativ sind wird 
		//standardmaessig die BS-Uhrzeit zugeordnet
		if(stunden < 0 || minuten < 0 || sekunden < 0){
			reset = Calendar.getInstance();
			stunden = reset.get(Calendar.HOUR_OF_DAY);
			minuten = reset.get(Calendar.MINUTE);
			sekunden = reset.get(Calendar.SECOND);
		} else {
			this.stunden = stunden;
			this.minuten = minuten;
			this.sekunden = sekunden;
		}
	}
	
	
	/**
	 * @return the stunden
	 */
	public int getStunden() {
		return stunden;
	}



	/**
	 * @return the minuten
	 */
	public int getMinuten() {
		return minuten;
	}



	/**
	 * @return the sekunden
	 */
	public int getSekunden() {
		return sekunden;
	}



	/**
	 * @param stunden the stunden to set
	 */
	public void setStunden(int stunden) {
		this.stunden = stunden;
	}



	/**
	 * @param minuten the minuten to set
	 */
	public void setMinuten(int minuten) {
		this.minuten = minuten;
	}



	/**
	 * @param sekunden the sekunden to set
	 */
	public void setSekunden(int sekunden) {
		this.sekunden = sekunden;
	}



	public void ausgebendeutsch(){
		System.out.printf("Uhrzeit: %d:%d:%d", stunden, minuten, sekunden);
	}
	
	/**
	 * Gibt Uhrzeit im englischen Format aus<br>
	 * Das Format entspricht der digitale Uhrzeit.
	 */
	public void ausgebenEnglisch(){
		/*
		 * 12-hour digital clock 
		 */
		if(stunden >=12 && stunden < 24){
			System.out.printf("Time: %d:%d:%d p.m.", stunden-12, minuten, sekunden);
		}
		if(stunden >= 24 && stunden < 12){
			System.out.printf("Time: %d:%d:%d a.m.", stunden-12, minuten, sekunden);
		}
	}
	/**
	 * Berechnet die Differenz in Sekunden zwischen zwei Uhrzeiten
	 * @param t2 die Zeit fuer die Berechnung
	 * @return der Unterschied in Sekunden als ganze Zahl
	 */
	public int differenz(Zeit t2){
		int st = t2.stunden;
		int mn = t2.minuten;
		int sc = t2.sekunden;
		Calendar param = Calendar.getInstance();
		param.set(Calendar.HOUR, st);
		param.set(Calendar.MINUTE, mn);
		param.set(Calendar.SECOND,sc);
		
		Calendar thisObj = Calendar.getInstance();
		thisObj.set(Calendar.HOUR, stunden);
		thisObj.set(Calendar.MINUTE, minuten);
		thisObj.set(Calendar.SECOND, sekunden);
		
		long millisT2 = param.getTimeInMillis();
		long millisThisObj = thisObj.getTimeInMillis();
		
		//Difference in Milliseconds
		long diff;
		if(thisObj.getTime().after(param.getTime())){
			System.out.print("Achtung: Die erste Uhrzeit kommt spaeter, als die zweite. "
					+ "Berechnung wird umgestellt: ");
			diff = millisThisObj - millisT2;
		} else {
			diff = millisT2 - millisThisObj;
		}
		//Differenz in Sekunden
		 long diffInSeconds = diff / 1000;
		 if(diffInSeconds == 0){
			 System.out.print("Die Uhrzeiten sind gleich: ");
		 }
		 
		 return (int) diffInSeconds;		
	}

}

