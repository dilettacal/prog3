
package bankprojekt.verarbeitung;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * Stellt einen Studenten dar
 * @author Diletta Cal
 * 21.10.2017
 *
 */
public class Student extends Kunde {
	
	/**
	 * Name der besuchten Universitaet
	 */
	private String uniName;
	/**
	 * Name des Studienfachs
	 */
	private String studienfach;
	
	/**
	 * Voraussichtliches Studienende
	 */
	private LocalDate studienende;
	
	/**
	 * Letztes Semester, in dem zum letzten Mal eine Studienbescheinigung
	 * vorgelegt wurde
	 */
	private int hochschulsemester; // fortlaufend gezaehlt
	
	
	/**
	 * Default-Konstruktor
	 */
	public Student(){		
		this("Max", "Musterstudent", "findeKeinZimmer", LocalDate.parse("1995-05-01"),
				"Uni", "Phantasiefach", LocalDate.now().plusYears(3), 1);
	}

	/**
	 * Erzeugt einen Studenten anhand der übergebenen Parameter
	 * @param vorname		Studentenvorname
	 * @param nachname		Studentennachname
	 * @param adresse		Adresse	
	 * @param gebdat		Geburtsdatum	
	 * @param uniName		Universitaetname
	 * @param studienfach	Studienfach
	 * @param studienende	vorauss. Studienende
	 * @param hochschulsemester		Semester der letzten Bescheinigung
	 * @throws DateTimeParseException wenn das Format des übergebenen Datums nicht korrekt ist
	 * @throws IllegalArgumentException falls einer der Parameter null ist
	 * 
	 */
	
	public Student(String vorname, String nachname, String adresse, LocalDate gebdat, String uniName,
			String studienfach, LocalDate studienende, int hochschulsemester) {
		super(vorname, nachname, adresse, gebdat);
		//Parameter auf Gueltigkeit pruefen
		if(uniName == null || studienfach == null || studienende == null)
			throw new IllegalArgumentException("Parameter muss gueltig sein");

		/*
		 * Standard-Wert 0:
		 * - fehlerhafte Eingabe (negative Zahl)
		 * - keine Studi-Bescheinigung eingetragen -> Studi-Status zu bestaetigen
		 * - vorlaeufiger Status, falls falsche Bescheinigung eingetragen wurde
		 */
		
		if(hochschulsemester <= 0){ 
			this.hochschulsemester = 0; 
		}
		else { 
			this.hochschulsemester = hochschulsemester; 			
		}	
		this.uniName=uniName;
		this.studienfach=studienfach;
		this.studienende=studienende;
		
	}

	/**
	 * Erzeugt einen Studenten anhand der übergebenen Parameter
	 * @param vorname		Studentenvorname
	 * @param nachname		Studentennachname
	 * @param adresse		Adresse	
	 * @param gebdat		Geburtsdatum	
	 * @param uniName		Universitaetname
	 * @param studienfach	Studienfach
	 * @param studienende	vorauss. Studienende
	 * @param hochschulsemester		Semester der letzten Bescheinigung
	 * @throws IllegalArgumentException falls einer der Parameter null ist
	 * 
	 */
	public Student(String vorname, String nachname, String adresse, String gebdat,  String uniName,
			String studienfach, String studienende, int hochschulsemester) {
		this(vorname, nachname, adresse, LocalDate.parse(gebdat, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
				uniName, studienfach, LocalDate.parse(studienende, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), hochschulsemester);
	
	}

	/**
	 * Liefert den Uninamen zurueck
	 * @return der Universitaetsname
	 */
	public String getUniName() {
		return uniName;
	}
	
	/**
	 * Setzt den Uninamen ein
	 * @param Universitaetsname
	 */
	public void setUniName(String uniName){
		if(uniName == null) throw new IllegalArgumentException("Parameter darf nicht null sein");
		else this.uniName = uniName;
	}

	/**
	 * Liefert besuchtes Fach zuerueck
	 * @return das Studienfach
	 */
	public String getStudienfach() {
		return studienfach;
	}

	
	
	/**
	 * Setzt ein neues Studienende ein.
	 * @param studienende das Datum als LocalDate Objekt
	 */
	public void setStudienende(LocalDate studienende) {
		if(studienende == null) throw new IllegalArgumentException();
		this.studienende = studienende;
	}
	
		
	/**
	 * Liefert Studienende-Datum zurueck
	 * @return Enddatum
	 */
	public LocalDate getStudienende() {
		return studienende;
	}
	
	

	/**
	 * @return the hochschulsemester
	 */
	public int getHochschulsemester() {
		return hochschulsemester;
	}
	
//	
//	/**
//	 * Moegliche Erweiterung: 
//	 * Setzt Verguenstigungsstatus zurueck - vorlaeufig oder weil Studi kein Studi mehr ist
//	 */
//	public void setStudiStatusZurueck(){
//		this.hochschulsemester = 0;
//	} 
//	
	/**
	 * Prueft, ob eine Studi-Bescheinigung vorliegt.
	 * @return true, wenn hochschulsemester groesser null,
	 * 			false, andernfalls
	 */
	public boolean istImmatrikuliert(){
		return hochschulsemester > 0;
	}
	
	/**
	 * Traegt eine neue Immatrikulationsbescheinigung ein.
	 * @param hochschulsemester Hochschulsemester auf Immatrikulationsbescheinigung
	 */
	public void bescheinigungEintragen(int hochschulsemester) throws IllegalArgumentException {
		//gueltige Bescheinigung - nur fortlaufend moeglich
		if(this.hochschulsemester <= hochschulsemester)
			this.hochschulsemester = hochschulsemester;
		//keine gueltige Bescheinigung
		else {
			//setStudiStatusZurueck(); 
			throw new IllegalArgumentException("Der Wert fuer das neue Semester muss groesser sein, "
					+ "als der Wert vom aktuellen Semester");
		}
	}

	
	@Override
	public String toString() {
		String ausgabe = super.toString();
		DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		ausgabe+= uniName + " " + studienfach + System.getProperty("line.separator") 
		+ " Voraussichtliches Studienende: " + df.format(studienende) + System.getProperty("line.separator");
		ausgabe+= (istImmatrikuliert())? " immatrikuliert im " + getHochschulsemester() +" Semester": " Bescheinigung fehlt oder Student nicht mehr immatrikuliert";
		return ausgabe;
	}
	
	
	

}
