package bankprojekt.verwaltung;
/**
 * 
 */

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import bankprojekt.verarbeitung.Beobachter;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.KontonummerNichtVorhandenException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verwaltung.logging.ExceptionLogger;


/**
 * Stellt eine Bank dar
 * @author Diletta Cal
 * 27.10.2017
 *
 */
public class Bank implements Serializable, Cloneable { 
	//Logger für Exception
	private ExceptionLogger exclog = ExceptionLogger.getInstanz();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	/**
	 * Bankleitzahl
	 */
	private long bankleitzahl; 
	
	/**
	 * speichert alle Konten der Bank
	 */
	private Map<Long, Konto> kontenliste = new HashMap<>();
	
	/**
	 * Speichert groesste vergebene Kontonummer
	 */
	//private long maxKontonummer = 1000000000;
	private long maxKontonummer = 0;
	
	/**
	 * Default-Konstruktor
	 */
	public Bank(){
		bankleitzahl = 12345678;
	}
	
	/**
	 * Kostruktor der Klasse Bank
	 * @param bankleitzahl die Bankleitzahl
	 * @throws IllegalArgumentException falls Paramter negativ 
	 */
	public Bank(long bankleitzahl){
		if(bankleitzahl > 0)
			this.bankleitzahl = bankleitzahl;
		else {
			IllegalArgumentException e = new IllegalArgumentException("Bankleitzahl darf nicht negativ sein!");
			exclog.exceptionLoggen(e);
			throw e;
		}
	}
	
	/**
	 * Getter der Eigenschaft Bankleitzahl
	 * @return die Bankleitzahl
	 */
	public long getBankleittahl(){
		return bankleitzahl;
	}
	
	/**
	 * Erstellt Girokonto fuer Kunde inhaber
	 * @param inhaber der Kunde
	 * @return Kontonummer
	 * @throws NullPointerException falls Kunde null ist
	 */
	
	public long girokontoErstellen(Kunde inhaber){
		if(inhaber != null){
			//vergibt eine gueltige Kontonummer
			long ktnummer = maxKontonummer+1;
			//erstellt neues Giro anhand der Kontonummer und des Kunden inhaber
			Konto giro = new Girokonto(inhaber, ktnummer, 1500);
			//fuegt kontonummer und giro in die Hashmap
			kontenliste.put(ktnummer,giro);
			//aktualisiert die bishervergebenen Kontonummer
			maxKontonummer = ktnummer;
			return ktnummer;
		}
		else {
			NullPointerException e = new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}
		
	}
	/**
	 * Erzeugt ein neues Sparbuchkonto
	 * @param inhaber der Inhaber
	 * @return Sparbuchkontonummer
	 * @throws NullPointerException wenn Kunde null
	 */
	public long sparbuchErstellen(Kunde inhaber) {
		if (inhaber != null) {
			// vergibt eine gueltige Kontonummer
			long ktnummer = maxKontonummer + 1;
			// erstellt neues Sparbuch anhand der Kontonummer und des Kunden inhaber
			Konto sparbuch = new Sparbuch(inhaber, ktnummer);
			// fuegt kontonummer und Sparbuch in die Hashmap
			kontenliste.put(ktnummer, sparbuch);
			// aktualisiert die bishervergebenen Kontonummer
			maxKontonummer = ktnummer;
			return ktnummer;
		} else{
			NullPointerException e = new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}
			
	}
	
	
	/**
	 * Gibt alle Konten - Kontonummer und Kontostand
	 * als String aus
	 * @return String-Ausgabe der Konten
	 */
	public String getAlleKonten(){
		String result="";
		//Long n: kontenliste.keySet() ruft nur keys auf!
		for(Map.Entry<Long, Konto> entry: kontenliste.entrySet()){
			result+="Kontonummer: " + entry.getKey() + " Kontostand: " + entry.getValue().getKontostand()
					+ System.getProperty("line.separator");
		}
		return result;
	}
	
	/**
	 * Liefert eine Liste mit allen Kontonummern
	 * @return Liste der Kontonummern
	 */
	public List<Long> getAlleKontonummern(){
		return kontenliste.keySet().stream().collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Hebt betrag vom Konto von
	 * @param von kontonummer
	 * @param betrag abzuhebendes Geld
	 * @return true, falls Operation moeglich
	 * 			false, wenn Operation nicht moeglich oder wenn Konto nicht vorhanden ist
	 * @throws GesperrtException
	 * @throws KontonummerNichtVorhandenException  
	 */
	public boolean geldAbheben(long von, double betrag) throws KontonummerNichtVorhandenException{
		if(kontenliste.containsKey(von)){
			Konto temp = kontenliste.get(von);
			//oder throws GesperrtException in der Methodensignatur
			try {
				return temp.abheben(betrag); //return (kontenliste.get(von)).abheben(betrag);
			} catch (GesperrtException e) {
				
				//Exception logging
				exclog.exceptionLoggen(e);
				return false;
			}
		}
		else{
			KontonummerNichtVorhandenException e = new KontonummerNichtVorhandenException(von);
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}
	}
	
	/**
	 * Zahlt betrag auf Konto auf ein
	 * @param auf das Konto
	 * @param betrag der Betrag
	 * @throws KontonummerNichtVorhandenException 
	 */
	public void geldEinzahlen(long auf, double betrag) throws KontonummerNichtVorhandenException{			
		if(kontenliste.containsKey(auf)){
			kontenliste.get(auf).einzahlen(betrag);
		} else {
			KontonummerNichtVorhandenException e = new KontonummerNichtVorhandenException(auf);
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}
	}
	
	/**
	 * loescht das Konto mit der angegebenen Kontonummer
	 * @param nummer die Kontonummer
	 * @return true, falls Loeschen moeglich
	 * 		false, falls Loeschen nicht moeglich
	 * @throws KontonummerNichtVorhandenException 
	 */
	public boolean kontoLoeschen(long nummer) throws KontonummerNichtVorhandenException{
		Konto k = kontenliste.remove(nummer);
		if(k!= null) return true;
		else {
			KontonummerNichtVorhandenException e = new KontonummerNichtVorhandenException(nummer);
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;		
		}
	}
	
	/**
	 * Liefert Kontostand vom Konto mit Kontonummer nummer
	 * @param nummer Kontonummer
	 * @return kontostand
	 * @throws KontonummerNichtVorhandenException 
	 */
	public double getKontostand(long nummer) throws KontonummerNichtVorhandenException{
		if(kontenliste.containsKey(nummer))
			return kontenliste.get(nummer).getKontostand();
		else{
			KontonummerNichtVorhandenException e = new KontonummerNichtVorhandenException(nummer);
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}

	}
	
	/**
	 * ueberweist den betrag vom Konto vonKontonr nach Konto nachKontonr
	 * @param vonKontonr Konto, von dem Ueberweisung gesendet wird
	 * @param nachKontonr Konto, das Ueberweisung empfaengt
	 * @param betrag Ueberweisungssumme
	 * @param verwendungszweck Verwendugszweck
	 * @return true, falls Operation moeglich
	 * 			false, falls Operation nicht moeglich
	 * @throws GesperrtException falls ein Konto gesperrt ist
	 */
	public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws GesperrtException{
		//1. Pruefe, ob Konten ueberhaupt vorhanden sind
		boolean checkVonKtn = kontenliste.containsKey(vonKontonr);
		boolean checkNachKtn = kontenliste.containsKey(nachKontonr);
		if(checkVonKtn && checkNachKtn){
			Girokonto von, nach; //Girokonto von = (Girokonto) kVon; muss nicht im Diagramm dargestellt werden!
			//2. Pruefe, dass die Konten Girokonten sind
			if(kontenliste.get(vonKontonr) instanceof Girokonto 
					&& kontenliste.get(nachKontonr) instanceof Girokonto){
				von = (Girokonto) kontenliste.get(vonKontonr);
				nach = (Girokonto) kontenliste.get(nachKontonr);
				//3. Falls vonKontonr nicht gesperrt
				//24.11. hier try catch mit einem boolean geklappt 
				if(!von.isGesperrt()){
					//sende Ueberweisung ab
					von.ueberweisungAbsenden(betrag, nach.getInhaber().getNachname(), nachKontonr, this.bankleitzahl, verwendungszweck);
					//empfange Ueberweisung
					nach.ueberweisungEmpfangen(betrag, von.getInhaber().getNachname(), vonKontonr, this.bankleitzahl, verwendungszweck);
					return true;
				}			
								
			}
		}
		return false;
		
	}
	
	//Blatt 8 - Lambda und Streams
	/**
	 * spertt alle Konten, deren Kontonstand im Minus ist
	 */
	public void pleitegeierSperren(){
		//Kontenliste ist Iterable, daher forEach
		kontenliste.values().forEach(k -> 
		{
			if(k.getKontostand() < 0)
				k.sperren();
		});
	
	}
	
	/**
	 * Liefert Liste aller Kunde, die auf einem Konto einen Kontostand haben,
	 * der mindestens minumum beträgt
	 * @param minimum
	 * @return
	 */
	public List<Kunde> getKundenMitVollemKonto(double minimum){
		return kontenliste.values()
				.stream()
				.collect(Collectors.partitioningBy(k->k.getKontostand()>= minimum)) //~> Map<Boolean, List<Konto>>
				.get(true) //nur Konten, die das Predicate erfüllen
				.stream() //erzeuge davon neues Stream
				.map(k->k.getInhaber()) //map konto zu kunden
				.distinct()
				.sorted(Comparator.comparing(Kunde::getNachname)) //sortiere die Kunden 
				.collect(Collectors.toList()); //Kunden als Liste
	}
	
	
	List<Kunde> getAlleReichenKunden(double minimum){
		return kontenliste.values().stream()
		.collect(Collectors.partitioningBy(k->k.getKontostand()> minimum)) //~> Map<Boolean, List<Konto>>
		.get(true) //nur Konten, die das Predicate erfüllen
		.stream() //erzeuge davon neues Stream
		.map(k->k.getInhaber()) //map konto zu kunden
		.distinct()
		.sorted(Comparator.comparing(Kunde::getNachname)) //sortiere die Kunden 
		.collect(Collectors.toList()); //Kunden als Liste
	}

	@Override
	public Bank clone(){
		byte[] bank = null; //byte-Array fuer die Umwandlung
		//1. Zu kopierendes Objekt wird in einen BAOS hineingeschrieben
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			//Objekt wird in einen OOS geschrieben
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			//Das Objekt wird in einen byte-Array gewandelt
			bank = baos.toByteArray();
		} catch (Exception e){
			exclog.exceptionLoggen(e);
//			e.printStackTrace();
		}
		//Kopie wird gelesen und zurückgeliefert
		try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bank))){
			return (Bank) ois.readObject();
			//Kann nichts passieren
		} catch (IOException | ClassNotFoundException e ){
			exclog.exceptionLoggen(e);
		
		} 
		
		return null;
	}
	
	//Blatt 10
	/**
	 * Erstellt ein Konto und speichert es.
	 * @param fabrik, die Fabrik zum Eröffnen eines Kontos von einer spezifischen Kontoart
	 * @param inhaber der Kontoinhaber
	 * @return vergebene Kontonummer
	 * @throws IllegalArgumentException wenn inhaber = null (hier oder in der Methode)
	 */
	public long kontoErstellen(Kontofabrik fabrik, Kunde inhaber){
		//Ueberprüfung erfolgt auch in den Konten-Klassen
		if(inhaber != null){
			long ktnummer = maxKontonummer+1;
			kontenliste.put(ktnummer, fabrik.erzeugen(inhaber, ktnummer));
			maxKontonummer = ktnummer;
			return ktnummer;
		}
		else {
			NullPointerException e = new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			//throw new NullPointerException("Bitte uebergeben Sie einen echten Kunden!");
			exclog.exceptionLoggen(e);
			throw e;
		}
	}

	
	/**
	 * Meldet Observer bei einem bestimmten Konto an
	 * @param b Observer
	 * @param konto das zu beobachtende Konto
	 */
	public void observerAnmelden(Beobachter b, long konto){
		kontenliste.get(konto).addObserver(b);
	}
	
	/**
	 * Meldet Observer von einem Konto ab
	 * @param b der Observer
	 * @param konto das beobachtete Konto
	 */
	public void observerAbmelden(Beobachter b, long konto){
		kontenliste.get(konto).removeObserver(b);		
	}
	
	/**
	 * Benachrichtigt alle angemeldeten Beobachter
	 * zu eventuellen Änderungen im Subjekt (k)
	 * @param k
	 */
	public static void observerBenachrichtigen(Konto k){
		k.getBeobachter().forEach(b -> ((Beobachter) b).update(k));
	}
	
	


}
