/**
 * 
 */
package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * @author Diletta Cal
 * 10.12.2017
 * @version
 *
 */
public class GirokontoFabrik extends Kontofabrik {
	
	private final int DEFAULT_DISPO = 500;

	@Override
	public Konto erzeugen(Kunde inhaber, long kontonummer) {
		return new Girokonto(inhaber,kontonummer, DEFAULT_DISPO);
	}
}
