/**
 * 
 */
package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * Abstrakte Fabrik Konto bietet die Oberfläche zu
 * den speziellen Kontoerzeugern
 * @author Diletta Cal
 * 09.12.2017
 * 
 */
public abstract class Kontofabrik {
	/**
	 * 
	 * @param inhaber
	 * @param kontonummer
	 * @return
	 * @throws IllegalArgumentException wenn inhaber = null
	 */
	public abstract Konto erzeugen(Kunde inhaber, long kontonummer);

}
