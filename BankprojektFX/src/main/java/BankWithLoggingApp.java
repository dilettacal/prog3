import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.KontonummerNichtVorhandenException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.logging.ExceptionLogger;

/**
 * 
 */

/**
 * @author Diletta Cal
 * 17.01.2018
 * @version
 *
 */
public class BankWithLoggingApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExceptionLogger exclog = ExceptionLogger.getInstanz();
		//Test mit einigen Exceptions
		Exception e = new NullPointerException("Ich bin eine NullPointerException!!!");
		exclog.exceptionLoggen(e);
		
		e = new IllegalArgumentException("Ungültiges Parameter!");
		exclog.exceptionLoggen(e);
		
		Throwable t = new Throwable("Alles kann passieren");
		exclog.exceptionLoggen(t);
		
		//Exceptions aus Bank und Konto
		Bank b;
		try{
			b = new Bank(-1);
		} catch (Exception ex){
		}
		
		b = new Bank(1400);
		try{
			b.sparbuchErstellen(null);
		} catch(NullPointerException npe){}
		
		try{
			b.geldEinzahlen(1000, 150);
		}catch (KontonummerNichtVorhandenException knv){
			
		}
		
		try{
			b.kontoLoeschen(1000);
		}catch (KontonummerNichtVorhandenException knv){
			exclog.exceptionLoggen(knv);
		}

		b.girokontoErstellen(Kunde.MUSTERMANN);
		try {
			b.geldAbheben(1000000001, 800);
		} catch (KontonummerNichtVorhandenException e1) {
			
		}
		Konto k = null;
		try{
			k = new Sparbuch(null, 1010);
		} catch (NullPointerException | IllegalArgumentException kontoex){
			
		}
		
		//exclog.displayLog(); //Anzeige in der Console
	}

}
