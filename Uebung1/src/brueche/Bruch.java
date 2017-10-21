package brueche;

import java.math.BigInteger;
/**
 * 
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 5.10.2017
 * Klasse Bruch zur Verwaltung von Bruch-Objekten.
 *
 */
public class Bruch implements Comparable<Bruch>{
	//Attribute der Klasse Bruch
	private int zaehler;
	private int nenner;
	
	
	/**
	 * Default-Konstruktor 
     * setzt als Zaehler und Nenner 1 als Default-Werte
	 */
	public Bruch(){
		zaehler = 1;
		nenner = 1;
	}
	
	/**
	 * Konstruktor der Klasse Bruch
	 * @param zaehler der Bruchzaehler
	 * @param nenner der Bruchnenner
	 */
	public Bruch(int zaehler, int nenner){
		this.zaehler = zaehler;
		if(nenner != 0){
			this.nenner = nenner;
		} else 
			throw new IllegalArgumentException("Nenner darf nicht gleich null sein!");
	}
	
	/**
	 * Liefert Zaehler zurueck
	 * @return Zaehler
	 */
	public int getZaehler() {
		return zaehler;
	}
	/**
	 * Liefert Nenner zueruck
	 * @return Nenner
	 */
	public int getNenner() {
		return nenner;
	}


	/**
	 * Berechnet die Multiplikation zweier Brueche
	 * @param b der zweite Bruch
	 * @return Ergebnis
	 */
	public Bruch multiplizieren(Bruch b){
		return new Bruch(this.zaehler*b.zaehler, this.nenner*b.nenner);	
	}
	
	/**
	 * Berechnet der Kehrwert eines Bruches
	 * @return Kehrwert
	 */
	public Bruch kehrwert(){ //this nicht veraendern!!!!
		if(zaehler == 0) throw new ArithmeticException("Operation nicht gueltig. "
				+ "Nenner darf nicht null sein!");
		int z = this.zaehler;
		int n = this.nenner;
		this.zaehler = n;
		this.nenner = z;
		return this;
	}
	
	/**
	 * Dividiert this mit Bruch b 
	 * @param b Bruch, durch den dividiert wird
	 * @return Divisionergebnis
	 */
	public Bruch dividieren(Bruch b){
		boolean erfolg = false;
		try{
			b.kehrwert();
			System.out.println("Kehrwert: " + b);
			erfolg = true;
		} catch (ArithmeticException e){
			System.out.println(e);
		}
		if(erfolg)
			return this.multiplizieren(b);
		else return null;
	}
	
	/**
	 * Kuerzt Bruch-Objekt 
	 */
	public void kuerzen() {
		if(this.zaehler == this.nenner) 
		{
			this.zaehler = 1;
			this.nenner = 1;
		}
		//berechnet GCD von Zaehler und Nenner
		int gcd = BigInteger.valueOf(this.zaehler).gcd(BigInteger.valueOf(this.nenner)).intValue();
		if(gcd != 1) //teilerfremd
		{	//Fuehrt Kuerzung aus
			this.zaehler = this.zaehler/gcd;
			this.nenner = this.nenner/gcd;	
		}
		//Signum wird gesetzt
		this.setSignum();
	}
	
	/**
	 * Rechnet einen Bruch aus
	 * @return Bruch als double-Wert
	 */
	public double ausrechnen(){
		if(this.nenner == this.zaehler) return 1;
		else {
			this.kuerzen();
			return (double) zaehler/nenner;
		}
	}
	
	/**
	 * Die Methode setzt das Bruchsignum
	 */
	private void setSignum(){
		//Das Signum wird dem Zaehler uebertragen
		int z, n;
		if(zaehler > 0 && nenner < 0){
			n = -nenner;
			nenner = n;
			zaehler = - zaehler;
		}
		else if(zaehler < 0 && nenner < 0){
			n = -nenner;
			z = -zaehler;
			zaehler = z;
			nenner = n;			
		}
	}
	
		
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Bruch b) {
		/*
		 * Die Methode erlaubt eine natuerliche aufsteigende Anordnung
		 */
		double erg = this.ausrechnen() - b.ausrechnen();
		if(erg == 0.0) return 0;
		else if(erg > 0.0) return 1;
		else return -1;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		this.kuerzen();
		String ergebnis="";
		if(zaehler == 0)
			ergebnis+= "0"; // 0/5 --> 0
		else if(nenner == 1){
			ergebnis += zaehler; //5/1 --> 5
		}
		else 
			ergebnis+= zaehler + "/" + nenner; //sonst
		return ergebnis;
	}


}
