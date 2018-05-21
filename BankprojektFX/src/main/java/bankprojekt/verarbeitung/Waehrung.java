package bankprojekt.verarbeitung;

import java.io.Serializable;

/**
 * Diese Klasse stellt Währung dar
 * @author Diletta Cal
 * 13.10.2017
 *
 */
public enum Waehrung implements Serializable{
	
	/**
	 * Konstanten der Klasse Waehrung oder einzeln beschreiben
	 */
	/**
	 * Euro
	 */
	EUR(1.00), 
	/**
	 * Leva Bulgarien
	 */
	BGN(1.95583), 
	LTL(3.4528), 
	KM(1.95583); 
	
	/**
	 * Setzt Waehrungskurs 
	 * @param kurs der Kurs
	 */
	private Waehrung(double kurs)
	{
		this.kurs = kurs;
	}
	
	/**
	 * Attribut Kurs
	 */
	private final double kurs;
	
	/**
	 * Getter fuer Attribut Kurs
	 * @return Waehrungskurs
	 */
	public double getKurs(){
		return this.kurs;
	}
	
	/**
	 * Rechnet den in EUR angegebenen Betrag in die jeweilige Waehrung um
	 * @param betrag Betrag in EUR
	 * @return Umrechnung in die Waehrung
	 */
	public double umrechnen(double betrag){
		return betrag*this.kurs;
	}

}
