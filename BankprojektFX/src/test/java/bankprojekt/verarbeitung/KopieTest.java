package bankprojekt.verarbeitung;
import static org.junit.Assert.*;

import org.junit.Test;

import bankprojekt.verwaltung.Bank;

/**
 * 
 */

/**
 * Tests der Methode zur Erstellung von Kopien der Klasse Bank
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 03.12.2017
 * @version
 * 
 */
public class KopieTest {
	
	private Bank b1 = new Bank(151963);
	private Bank b2;
	@Test
	public void CloneTest() throws KontonummerNichtVorhandenException {
		b1.girokontoErstellen(Kunde.MUSTERMANN);
		b2 = b1.clone(); //Wenn clone Bank Obj liefert, dann brauchen wir hier kein cast
		if(!b2.equals(null)){
			b1.geldEinzahlen(1000000001, 150);
			//Kontostand in b2 nicht verändert
			assertTrue(b2.getKontostand(1000000001) == 0);
			//Kontostand in b1 
			assertTrue(b1.getKontostand(1000000001) == 150);
			//Kontoständer sind unterschiedlich
			assertFalse(b2.getKontostand(1000000001) == b1.getKontostand(1000000001));
		} else {
			//falls Kopie fehlgeschlagen
			fail();
		}
		//kein try-catch notwendig
	}

}
