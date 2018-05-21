package bankprojekt.verarbeitung;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * **** Class provided by instructor! *****
 * <p>
 * Kunde einer Bank
 * </p>
 * 
 * @author doro
 * @version 1.0
 */
public class Kunde implements Comparable<Kunde>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ein Musterkunde
	 */
	public static final Kunde MUSTERMANN = new Kunde("Max", "Mustermann", "zuhause", LocalDate.now());
	
	/**
	 * englische oder deutsche Anrede, je nach den Systemeinstellungen
	 */
	private static String ANREDE;

	/**
	 * liefert die systemspezifische Anrede
	 * @return
	 */
	public static String getANREDE() {
		return ANREDE;
	}

	/**
	 * der Vorname
	 */
	private String vorname;
	/**
	 * Der Nachname
	 */
	private String nachname;
	/**
	 * Die Adresse
	 */
//	private String adresse;
	private StringProperty adresse = new SimpleStringProperty();
	/**
	 * Geburtstag
	 */
	private LocalDate geburtstag;

	/**
	 * erzeugt einen Standardkunden
	 */
	public Kunde() {
		this("Max", "Mustermann", "Adresse", LocalDate.now());
	}

	/**
	 * Erzeugt einen Kunden mit den übergebenen Werten
	 * 
	 * @param vorname
	 * @param nachname
	 * @param adresse
	 * @param gebdat
	 * @throws IllegalArgumentException wenn einer der Parameter null ist
	 */
	public Kunde(String vorname, String nachname, String adresse, LocalDate gebdat) {
		if(vorname == null || nachname == null || adresse == null || gebdat == null)
			throw new IllegalArgumentException("null als Parameter nich erlaubt");
		this.vorname = vorname;
		this.nachname = nachname;
		setAdresse(adresse);
		this.geburtstag = gebdat;
	}

	/**
	 * Erzeugt einen Kunden mit den übergebenen Werten
	 * 
	 * @param vorname
	 * @param nachname
	 * @param adresse
	 * @param gebdat
	 *            im Format tt.mm.yy
	 * @throws DateTimeParseException wenn das Format des übergebenen Datums nicht korrekt ist
	 * @throws IllegalArgumentException wenn einer der Parameter null ist
	 */
	public Kunde(String vorname, String nachname, String adresse, String gebdat)  {
		this(vorname, nachname, adresse, LocalDate.parse(gebdat,DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
		Zerstoerer z = new Zerstoerer();
		Thread t = new Thread(z);
		Runtime.getRuntime().addShutdownHook(t);
	}
	
	/**
	 * Klasse für Aufräumarbeiten
	 * @author Doro
	 *
	 */
	private class Zerstoerer implements Runnable
	{
		@Override
		public void run() {
			System.out.println("Kunde zerstört");
		}
	}

	/**
	 * gibt alle Daten des Kunden aus
	 */
	@Override
	public String toString() {
		String ausgabe;
		DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		ausgabe = this.vorname + " " + this.nachname + System.getProperty("line.separator");
		ausgabe += "Wohnhaft in: " + this.getAdresse() + System.getProperty("line.separator");
		ausgabe += "Geburtsdatum: " + df.format(this.geburtstag) + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * vollständiger Name des Kunden in der Form "Nachname, Vorname"
	 * 
	 * @return
	 */
	public String getName() {
		return this.nachname + ", " + this.vorname;
	}

	/**
	 * Adresse des Kunden
	 * 
	 * @return
	 */
	public String getAdresse() {
		return adresse.get();
	}

	/**
	 * setzt die Adresse auf den angegebenen Wert
	 * 
	 * @param adresse
	 * @throw IllegalArgumentException wenn adresse null ist
	 */
	public void setAdresse(String adresse) {
		if(adresse == null)
			throw new IllegalArgumentException("Adresse darf nicht null sein");
		this.adresse.set(adresse);
	}

	/**
	 * Liefert Adresse-Property zurück
	 * @return
	 */
	public StringProperty adresseProperty(){
		return adresse;
	}
	/**
	 * Nachname des Kunden
	 * 
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * setzt den Nachnamen auf den angegebenen Wert
	 * 
	 * @param nachname
	 * 	 * @throw IllegalArgumentException wenn nachname null ist
	 */
	public void setNachname(String nachname) {
		if(nachname == null)
			throw new IllegalArgumentException("Nachname darf nicht null sein");
		this.nachname = nachname;
	}

	/**
	 * Vorname des Kunden
	 * 
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * setzt den Vornamen auf den angegebenen Wert
	 * 
	 * @param vorname
	 * @throw IllegalArgumentException wenn vorname null ist
	 */
	public void setVorname(String vorname) {
		if(vorname == null)
			throw new IllegalArgumentException("Vorname darf nicht null sein");
		this.vorname = vorname;
	}

	/**
	 * Geburtstag des Kunden
	 * 
	 * @return
	 */
	public LocalDate getGeburtstag() {
		return geburtstag;
	}
	
	public void setGeburtstag(String gebdat){
		geburtstag =  LocalDate.parse(gebdat,DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
	}

	@Override
	public int compareTo(Kunde arg0) {
		return this.getName().compareTo(arg0.getName());
	}
	
	/**
	 * Destruktor
	 */
//	protected void finalize()
//	{
//		System.out.println("Kunde weg");
//	}
	
	static
	{
		if(Locale.getDefault().getCountry().equals("DE"))
			ANREDE = "Hallo Benutzer!";
		else
			ANREDE = "Dear Customer!";
	}
}