package bankprojekt.verarbeitung;

/**
 * Partially provided by the instructor. 
 * 
 * Ein Girokonto
 * @author Doro
 * @author Diletta Cal
 * 
 * Ergaenzung zur VL
 *
 */
public class Girokonto extends Konto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Wert, bis zu dem das Konto überzogen werden darf
	 */
	private double dispo;

	/**
	 * erzeugt ein Standard-Girokonto
	 */
	public Girokonto()
	{
		super(Kunde.MUSTERMANN, 99887766);
		this.dispo = 500;
	}
	
	/**
	 * erzeugt ein Girokonto mit den angegebenen Werten
	 * @param inhaber Kontoinhaber
	 * @param nummer Kontonummer
	 * @param dispo Dispo
	 * @throws IllegalArgumentException wenn der inhaber null ist 
	 * 									oder der angegebene dispo negativ ist
	 */
	public Girokonto(Kunde inhaber, long nummer, double dispo)
	{
		super(inhaber, nummer);
		if(dispo < 0)
			throw new IllegalArgumentException("Der Dispo darf nicht negativ sein!");
		this.dispo = dispo;
	}
	
	/**
	 * liefert den Dispo
	 * @return Dispo von this
	 */
	public double getDispo() {
		return dispo;
	}

	/**
	 * setzt den Dispo neu
	 * @param dispo muss größer sein als 0
	 * @throw IllegalArgumentException wenn dispo negativ ist
	 */
	public void setDispo(double dispo) {
		if(dispo < 0)
			throw new IllegalArgumentException("Der Dispo darf nicht negativ sein!");
		this.dispo = dispo;
	}
	
    /**
     * vermindert den Kontostand um den angegebenen Betrag, falls das Konto nicht gesperrt ist.
     * Am Empfängerkonto wird keine Änderung vorgenommen, da davon ausgegangen wird, dass dieses sich
     * bei einer anderen Bank befindet.
     * @param betrag double
     * @param empfaenger String
     * @param nachKontonr int
     * @param nachBlz int
     * @param verwendungszweck String
     * @return boolean
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der Betrag negativ ist oder
     * 									empfaenger oder verwendungszweck null ist
     */
    public boolean ueberweisungAbsenden(double betrag, 
    		String empfaenger, long nachKontonr, 
    		long nachBlz, String verwendungszweck) 
    				throws GesperrtException 
    {
      if (this.isGesperrt())
            throw new GesperrtException(this.getKontonummer());
        if (betrag < 0 || empfaenger == null || verwendungszweck == null)
            throw new IllegalArgumentException("Betrag negativ");
        if (getKontostand() - betrag >= - dispo) //Verfuegbarkeit nicht < -dispo! 
        {
            setKontostand(getKontostand() - betrag);
            return true;
        }
        else{
        	//Kontostand ist im Minus
        	setKontostandImMinus(true);
        	return false;
        }
        	
    }

    /**
     * erhöht den Kontostand um den angegebenen Betrag
     * @param betrag double
     * @param vonName String
     * @param vonKontonr int
     * @param vonBlz int
     * @param verwendungszweck String
     *      * @throws IllegalArgumentException wenn der Betrag negativ ist oder
     * 									vonName oder verwendungszweck null ist
     */
    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck)
    {
        if (betrag < 0 || vonName == null || verwendungszweck == null)
            throw new IllegalArgumentException("Betrag negativ");
        setKontostand(getKontostand() + betrag);
    }
    
    @Override
    public String toString()
    {
    	String ausgabe = "-- GIROKONTO --" + System.lineSeparator() +
    	super.toString() + System.lineSeparator() 
    	+ "Dispo für Girokonto: " + this.getDispo() + System.lineSeparator();
    	return ausgabe;
    }

    
	/**
	 * @author Diletta Cal
	 * Implementiert Methode aus Oberklasse
	 */
	public void kontospezifisch(Waehrung w){
		Waehrung aktuell = getAktuelleWaehrung();
		setDispo((dispo/aktuell.getKurs())*w.getKurs());
		
	}

	/**
	 * @author Diletta Cal
	 */
	@Override
	protected boolean pruefeVorbedingungen(double betrag) {
		//prueft, ob Abheben möglich hinsichtlich Kontostandes bzw. Dispos
		return getKontostand() - betrag >= - dispo;
	}

}
