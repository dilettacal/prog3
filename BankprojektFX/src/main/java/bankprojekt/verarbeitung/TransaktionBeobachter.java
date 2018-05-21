/**
 * 
 */
package bankprojekt.verarbeitung;

import java.io.Serializable;

/**
 * Konkreter Beobachter zu den Banktransaktionen
 * 
 * @author Diletta Cal
 * 20.12.2017
 *
 */
public class TransaktionBeobachter implements Beobachter, Serializable {

	
	private static final long serialVersionUID = 1L;
	/**
	 * TransaktionBeobachter besitzt alle Attribute
	 * die fuer eine Banktransaktion von auﬂen 
	 * notwendig sind
	 */
	private Kunde k;
	private long kntnummer;
	private double kntstand;
	private Waehrung w;
	private boolean gesperrt;

	//Konto konto;

	/**
	 * Konstruktor
	 */
	public TransaktionBeobachter() {
		k = Kunde.MUSTERMANN;
		kntnummer = 0;
		kntstand = 0;
		w = Waehrung.EUR;
		gesperrt = false;
	}

	
	protected String ausgabe() {
		return 	"-------- Zusammenfassung der Transaktion --------"
				+ System.lineSeparator()
				+ "Konto: " + kntnummer + System.lineSeparator() 
				+ "Waehrung: " + w + System.lineSeparator()
				+ "Kontostand: " + kntstand + System.lineSeparator() +
				"Kunde: " + k.getVorname() + " " + k.getNachname() + System.lineSeparator() 
				+ "gesperrt: " + gesperrt + System.lineSeparator();
	}

	@Override
	public void update(Konto subjekt) {
		//Zustand vom Subjekt wird ausgelesen
		this.kntnummer = subjekt.getKontonummer();
		this.kntstand = subjekt.getKontostand();
		this.w = subjekt.getAktuelleWaehrung();
		this.k = subjekt.getInhaber();
		this.gesperrt = subjekt.isGesperrt();
		//Alle Zust‰nde werden ausgegeben
		System.out.println(ausgabe());
	}

}
