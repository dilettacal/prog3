package brueche;

import java.util.Comparator;

/**
 * @author Diletta Calussi
 * Klasse ZaehlerVergleicher implementiert Comparator<br>
 * Die Klasse vergleicht zwei Bruechen anhand ihrer Zaehler.
 *
 */
public class ZaehlerVergleicher implements Comparator<Bruch> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Bruch a, Bruch b) {
		a.kuerzen();
		b.kuerzen();
		return a.getZaehler() - b.getZaehler();
	}
	

}
