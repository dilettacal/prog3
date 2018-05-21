package bankprojekt.verarbeitung;
/**
 * **** Class provided by instructor! *****
 * 
 * Enum fuer Kontoarten
 
 */
public enum Kontoart {
	GIROKONTO("mit ganz viel Dispo"), SPARBUCH("mit wenig Zinsen"), 
	FESTGELDKONTO("nur für die Fantasie");
	
	private String beschreibung;

	/**
	 * liefert einen Werbetext
	 * @return
	 */
	public String getBeschreibung() {
		return beschreibung;
	}
	
	private Kontoart(String b)
	{
		this.beschreibung = b;
	}
	
}
