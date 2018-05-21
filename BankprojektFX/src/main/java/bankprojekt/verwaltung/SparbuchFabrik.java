/**
 * 
 */
package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * @author Diletta Cal
 * 10.12.2017
 * @version
 *
 */
public class SparbuchFabrik extends Kontofabrik{
	
	/* (non-Javadoc)
	 * @see bankprojekt.verarbeitung.Kontofabrik#erzeugen(bankprojekt.verarbeitung.Kunde, long)
	 */
	@Override
	public Konto erzeugen(Kunde inhaber, long kontonummer) {
		return new Sparbuch(inhaber, kontonummer);
	}
}
