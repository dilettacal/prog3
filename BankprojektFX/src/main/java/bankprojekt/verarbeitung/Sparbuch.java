package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * **** Class partially provided by instructor! *****
 * 
 * ein Sparbuch
 * @author Doro
 * @author Diletta Cal
 *
 */
@SuppressWarnings("serial")
public class Sparbuch extends Konto {
	/**
	 * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
	 */
	private double zinssatz;
	
	/**
	 * Monatlich erlaubter Gesamtbetrag für Abhebungen
	 */
	public static final double ABHEBESUMME = 2000; //statische Konstante
	
	/**
	 * Betrag, der im aktuellen Monat bereits abgehoben wurde
	 */
	private double bereitsAbgehoben = 0;
	/**
	 * Monat und Jahr der letzten Abhebung
	 */
	private LocalDate zeitpunkt = LocalDate.now();
	
	/**
	 * Default-Konstruktor
	 */
	public Sparbuch() {
		zinssatz = 0.03;
	}

	/**
	 * Parameterkonstruktor
	 * @param inhaber der Kontoinhaber
	 * @param kontonummer die Kontonummer
	 */
	public Sparbuch(Kunde inhaber, long kontonummer) {
		super(inhaber, kontonummer);
		zinssatz = 0.03; //Default-Wert fuer Zinsen
	}
	
	@Override
	public String toString()
	{
    	String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
    	super.toString()
    	+ "Zinssatz: " + this.zinssatz * 100 +"%" + System.lineSeparator();
    	return ausgabe;
	}


	/*
	 * Implementierung von abheben aus der Oberklasse. Passt ABHEBESUMME an der Waehrung an.
	 * @see bankprojekt.verarbeitung.Konto#abheben(double)
	 */
	@Override
	/*
	public boolean abheben (double betrag) throws GesperrtException{
		if (betrag < 0 ) {
			throw new IllegalArgumentException();
		}
		if(this.isGesperrt())
		{
			GesperrtException e = new GesperrtException(this.getKontonummer());
			throw e;
		}
		LocalDate heute = LocalDate.now();
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = 0;
		}
		if (getKontostand() - betrag >= 0.50 && 
				//Passt ABHEBESUMME an der aktuellen Kontowaehrung an
				 bereitsAbgehoben + betrag <= Sparbuch.ABHEBESUMME*getAktuelleWaehrung().getKurs())
		{
			setKontostand(getKontostand() - betrag);
			bereitsAbgehoben += betrag;
			this.zeitpunkt = LocalDate.now();
			return true;
		}
		else
			return false;
	}
	*/
	
	/*
	 * ohne template:
	 * bereitsAbgehoben umrechnen
	 * super.waehrungswechsel(w);
	 */
//	public void waehrungswechsel(Waehrung w) {
//		Waehrung aktuell = this.getAktuelleWaehrung();
//		//Anpassung der bereits abgehobenen Summe
//		bereitsAbgehoben = (bereitsAbgehoben/aktuell.getKurs())*w.getKurs();
//		super.waehrungswechsel(w);
//	}
	
	/**
	 * @author Diletta Cal
	 */
	public void kontospezifisch(Waehrung w){
		Waehrung aktuell = this.getAktuelleWaehrung();
		bereitsAbgehoben = (bereitsAbgehoben/aktuell.getKurs())*w.getKurs();
	}



	@Override
	protected boolean pruefeVorbedingungen(double betrag) {
		LocalDate heute = LocalDate.now();
		//prueft ob neuer Monat angefangen hat
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = 0; //sollte hier nichts ändern
		}
		//Pruefung, ob Kontostand ausreichend
		return getKontostand() - betrag >= 0.50 && 
				//Passt ABHEBESUMME an der aktuellen Kontowaehrung an
				 bereitsAbgehoben + betrag <= Sparbuch.ABHEBESUMME*getAktuelleWaehrung().getKurs();
	
		/*
		 * Methode sollte nur testen -> bereitsAbgehoben wird auf 0 geetzt
		 * double abgehoben = this.bereitsAbgehoben;
		 * und danach
		 * if(getKontostand() - betrag >= 0.50 && 
				 abgehoben + betrag <= Sparbuch.ABHEBESUMME*getAktuelleWaehrung().getKurs()) 
				 return true;
			So kann man die Methode public machen
		 */
	
	}
	
	@Override
	protected void weitereAnpasungenPostAbheben(double betrag){
		//Anpassung des Datums
		zeitpunkt = LocalDate.now();
		//Anpassung bereitsAbgehoben
		bereitsAbgehoben += betrag;
		
	};

}
