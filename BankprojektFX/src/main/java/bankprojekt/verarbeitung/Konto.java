
package bankprojekt.verarbeitung;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import bankprojekt.verwaltung.Bank;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Class partially provided by the instructor
 * 
 * Stellt ein allgemeines Konto dar.<br>
 * Ergaenzung zu der Klasse aus der Vorlesung.
 * 
 * @author Diletta Cal
 * 14.10.2017
 * 
 */
public abstract class Konto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * @author Diletta Cal
	 * der aktuelle Kontostand
	 */
//	private double kontostand;
	private ReadOnlyDoubleWrapper kontostand;
	
	/**
	 * @author Diletta Cal
	 * Zeigt, ob Kontostand in Plus oder Minus ist.
	 * Kontostand == 0 ist true.
	 */
	private ReadOnlyBooleanWrapper kontostandImMinus;
	
	/**
	 * @author Diletta Cal
	 */
	private BooleanProperty negativerKontostand = new SimpleBooleanProperty(false);
	
	
	
	/**
	 * @author Diletta Cal
	 * die Waehrung, in die das Konto gespeichert wird
	 */
	private Waehrung waehrung;
	
	//Blatt 11
	
	/**
	 * @author Diletta Cal
	 * Liste der Beobachter
	 */
	private List<Beobachter> kontobeobachter;
	
	/**
	 * @author Diletta Cal
	 * liefert Beobachterliste zurück
	 * @return
	 */
	public List<Beobachter> getBeobachter(){
		return kontobeobachter;
	}

	
	/**
	 * @author Diletta Cal
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		//this.kontostand = kontostand;
		this.kontostand.set(kontostand);
		//hier kontoinminus aktualisieren!
		if(kontostand > 0) setKontostandImMinus(false);
		else setKontostandImMinus(true);
		notifyObservers();
	}
	/**
	 * @author Diletta Cal
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public ReadOnlyDoubleProperty kontostandProperty(){
		return kontostand.getReadOnlyProperty();
	}
	
	/**
	 * @author Diletta Cal
	 * Liefert double-Wert zurück
	 * @return
	 */
	public double getKontostand(){
		return kontostand.get();
	}
//	public final double getKontostand() { return kontostand;}


	/**
	 * @author Diletta Cal
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
//	private boolean gesperrt;
	private BooleanProperty gesperrt;

	//provided
	public Konto(Kunde inhaber){
		this.inhaber = inhaber;
		this.nummer = 123459;		
	}
	/**
	 * @author Diletta Cal
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber Kunde
	 * @param kontonummer long
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		//this.kontostand = 0;
		//Kontostand-Property wird initialisiert
		this.kontostand = new ReadOnlyDoubleWrapper();
		this.gesperrt = new SimpleBooleanProperty(false);
		this.waehrung = Waehrung.EUR; 
		//Initialisierung der Beobachterliste
		kontobeobachter = new LinkedList<>();
		
		//kontostand bei Erzeugung nicht im Minus
		this.kontostandImMinus = new ReadOnlyBooleanWrapper(false);
		
	}
	
	/**
	 * provided
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567); 
//		this.waehrung = Waehrung.EUR;
//		kontobeobachter = new LinkedList<>();
	}

	

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   Kunde
	 */
	public final Kunde getInhaber() {
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(isGesperrt())
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;
		notifyObservers();
	
	}
	

	/**
	 * liefert die  zurück
	 * @return   long
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * @author Diletta Cal
	 * Getter für gesperrten Zustand,
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return
	 */
	public final boolean isGesperrt() {
		//return gesperrt;
		return gesperrt.get();
	}
	
	/**
	 * @author Diletta Cal
	 * Setter für gesperrt
	 * @param gesperrt
	 */
	protected void setGesperrt(boolean gesperrt){
		this.gesperrt.set(gesperrt);
	}

	/**
	 * @author Diletta Cal
	 * Liefert BooleanProperty vom gesperrt-Zustand
	 * @return
	 */
	public BooleanProperty gesperrtProperty(){
		return gesperrt;
	}
	
	/**
	 * @author Diletta Cal
	 * Liefert Zustand vom Konto (minus oder plus)
	 * @return true, wenn im Plus
	 * 			false, andernfalls
	 */
	public boolean getKontostandImMinus(){
		return this.kontostandImMinus.get();
	}
	
	/**
	 * @author Diletta Cal
	 * Ändert Zustand vom kontostandImMinus-Property
	 * @param wert
	 */
	public void setKontostandImMinus(boolean wert){
		this.kontostandImMinus.set(wert);
	}
	
	/**
	 * @author Diletta Cal
	 * @return ReadOnlyBooleanProperty 
	 */
	public ReadOnlyBooleanProperty kontostandImMinusProperty(){
		return this.kontostandImMinus.getReadOnlyProperty();
	}
	
	/**
	 * @author Diletta Cal
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0) {
			throw new IllegalArgumentException("Negativer Betrag");
		}
		setKontostand(getKontostand() + betrag);
		notifyObservers();
	}
	
	/**
	 * Method provided
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
	 */
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Aktueller Kontostand: " + this.getKontostand() + " " + getAktuelleWaehrung() + System.lineSeparator();
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		
		return ausgabe;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	//public abstract boolean abheben(double betrag) throws GesperrtException;
	
	/**
	 * @author Diletta Cal
	 * Template method für das Abheben
	 * @param betrag der abgehoben werden muss
	 * @return true, falls Abheben moeglich
	 * 			false, andernfalls
	 * @throws IllegalArgumentException wenn Betrag negativ
	 * @throws GesperrtException wenn Konto gesperrt
	 * -final weg für Mocking
	 */
	public boolean abheben(double betrag) throws IllegalArgumentException, GesperrtException {
		if(betrag < 0) throw new IllegalArgumentException("Betrag darf nicht negativ sein.");
		if(isGesperrt()) throw new GesperrtException(this.getKontonummer());
		//prueft Vorbedingungen: Kontostand ausreichend hinsichtlich Dispos, monatl. Höchstbetrag
		if (pruefeVorbedingungen(betrag)) {
			//passt Kontostand an
			setKontostand(kontostand.get() - betrag);
			//Erledigt weitere Anpassungen - sofern notwendig
			weitereAnpasungenPostAbheben(betrag);
			notifyObservers();
			return true; //das muss hier
		}
		return false;
		
	}
	/*
	 * Aus Tafel:
	 * if(abhebeVoraussetzung(betrag)){
	 * 	setKontostand(kontostand-betrag);
	 * 	abhebeAbhschluss(betrag);
	 * 	return true;
	 */
	
	/**
	 * @author Diletta Cal
	 * Prueft, ob durch das Abheben die keine grundlegenden Kontobedingungen verletzt werden
	 * @return true, falls sie verletzt werden
	 * 			false, falls nicht
	 */
	protected abstract boolean pruefeVorbedingungen(double betrag);
	
	/**
	 * @author Diletta Cal
	 * Nimmt weitere Anpassungen vor, sofern sie für das 
	 * spezifische Konto notwendig sind
	 */
	protected void weitereAnpasungenPostAbheben(double betrag){};
	
	
	/**
	 * @author Diletta Cal
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public final void sperren() {
		this.gesperrt.set(true);
		notifyObservers();
	}

	/**
	 * @author Diletta Cal
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public final void entsperren() {
		this.gesperrt.set(false);
		notifyObservers();
	}
	
	
	/**
	 * Method provided.
	 * 
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public final String getGesperrtText()
	{
		if (isGesperrt())
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * Method provided.
	 * 
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.getKontonummer());
	}
	
	/**
	 * Method provided.
	 * 
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f Euro" , this.getKontostand());
	}
	
	/**
	 * Liefert  formatierten Kontostand mit Vorzeichen
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Vorzeichen
	 */
//	public StringProperty getKontoStandFormatiertMitVorzeichen(){
//		return String.format("%+-10.2f Euro" , this.kontostandProperty().asString());
//	}
	
	/**
	 * Method provided.
	 * 
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}
	
	/**
	 * @author Diletta Cal
	 * Hebt den gewuenschten Betrag in der angegebenen Waehrung ab
	 * @param betrag der abzuhebende Betrag
	 * @param w die Waehrung
	 * @return true, wenn Abheben moeglich
	 * 			false, wenn Abheben nicht moeglich ist
	 * @throws GesperrtException falls Konto gesperrt
	 */
	public boolean abheben (double betrag, Waehrung w) throws GesperrtException{
		//1. Umrechnung in EUR
		//2. Umrechnung in w
		double b = (betrag/w.getKurs())*this.waehrung.getKurs();
		return this.abheben(b); //ruft abheben(double) mit umgerechnetem Betrag auf
	}
	
	/**
	 * @author Diletta Cal
	 * Zahlt den angegebenen Betrag in der angegebenen Waehrung ein
	 * @param betrag der einzuzahlende Betrag
	 * @param w die Waehrung des Betrags
	 */
	public void einzahlen(double betrag, Waehrung w){
		//Umrechnung nach EUR, danach nach Kontowaehrung
		this.einzahlen((betrag/w.getKurs())*this.waehrung.getKurs());
	}
	
	/**
	 * @author Diletta Cal
	 * liefert Waehrungsart zurueck
	 * @return Kontowaehrung
	 */
	public Waehrung getAktuelleWaehrung() {
		return waehrung;
	}
	
	
	/**
	 * @author Diletta Cal
	 * Setzt eine neue Waehrung fuer das Konto. 
	 * @param w die neue Waehrung
	 */
	public final void waehrungswechsel(Waehrung w){
		setKontostand((kontostand.get()/this.waehrung.getKurs())*w.getKurs());
		this.waehrung = w;
		kontospezifisch(w);
		notifyObservers();		
	}
	
	/**
	 * @author Diletta Cal
	 * @param w
	 */
	public abstract void kontospezifisch(Waehrung w);
	
	/**
	 * Method provided.
	 * 
	 * Ausgabe auf Konsole
	 */
	public void aufKonsole()
	{
		System.out.println(this.toString());
	}
	
	/*
	 * Blatt 11
	 * Methode zur Anmeldung, Abmeldung und Benachrichtigung von Observern
	 */
	/**
	 * @author Diletta Cal
	 * Meldet Beobachter an
	 * @param ob der Beobachter
	 */
	public void addObserver(Beobachter ob){
		kontobeobachter.add(ob);
	}
	
	/**
	 * @author Diletta Cal
	 * Meldet Beobachter ab
	 * @param b
	 */
	public void removeObserver(Beobachter b){
		kontobeobachter.remove(b);
	}
	
	/**
	 * @author Diletta Cal
	 */
	protected void notifyObservers(){
		Bank.observerBenachrichtigen(this);
	}
	
	
}
