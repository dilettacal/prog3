package bankprojekt.verarbeitung;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verarbeitung.Waehrung;

/**
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 14.10.2017
 * JUnit-Test fuer die Klasse Konto
 *
 */
public class KontoTest {
	
	private Konto k, s;
	private double betrag = 200;
	private Waehrung abhebeWaehrung = Waehrung.BGN;

	
	@Before
	public void setUp() throws Exception {
		k = new Girokonto();
		k.einzahlen(1500);
		s = new Sparbuch();
		s.einzahlen(1500);
	}

	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#getAktuelleWaehrung()}.
	 */
	@Test
	public void testGetAktuelleWaehrung() {
		//Standard=EUR
		assertEquals(true, (k.getAktuelleWaehrung()==Waehrung.EUR) 
				&& (s.getAktuelleWaehrung() == Waehrung.EUR));
	}


	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#waehrungswechsel(bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test
	public void testWaehrungswechsel() {
		k.waehrungswechsel(Waehrung.BGN);
		s.waehrungswechsel(Waehrung.KM);
		assertEquals(true, (k.getAktuelleWaehrung() == Waehrung.BGN) 
				&& (s.getAktuelleWaehrung() == Waehrung.KM) );
	}
	
	//Abheben fuer Girokonto
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#abheben(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test(expected=GesperrtException.class)
	public void testAbhebenDoubleWaehrungStandardGiroThrowsException() throws GesperrtException {
		k.sperren();
		k.abheben(betrag, abhebeWaehrung);		
	}
	
	
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#abheben(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test
	public void testAbhebenDoubleWaehrungStandardGiro() throws GesperrtException {
		k.abheben(betrag, abhebeWaehrung);
		assertEquals(true,k.getKontostand()==1397.7416237607563);
		
	}
	
	//Abheben fuer Sparbuch
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#abheben(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test(expected=GesperrtException.class)
	public void testAbhebenDoubleWaehrungStandardSparbuchThrowsException() throws GesperrtException {
		s.sperren();
		abhebeWaehrung = Waehrung.LTL;
		s.abheben(betrag,abhebeWaehrung);
	}
	
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#abheben(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test
	public void testAbhebenDoubleWaehrungStandardSparbuch() throws GesperrtException{
		abhebeWaehrung = Waehrung.LTL;
		s.abheben(betrag,abhebeWaehrung);
		assertEquals(true,s.getKontostand()==1442.0759962928637);		
	}

	//Einzahlen Giro
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#einzahlen(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test
	public void testEinzahlenDoubleWaehrungStandardGiro() {
		//Einzahlen Girokonto - Girokonto verfuegt ueber 1500 EUR
		Waehrung w = Waehrung.LTL;
		k.einzahlen(betrag,w);		
		assertTrue(k.getKontostand() == 1557.9240037071363);
	}
	
	//Einzahlen Sparbuch
	
	/**
	 * Testmethode fuer {@link bankprojekt.verarbeitung.Konto#einzahlen(double, bankprojekt.verarbeitung.Waehrung)}.
	 */
	@Test
	public void testEinzahlenDoubleWaehrungStandardSparbuch() {
		//Einzahlen Sparbuch - Sparbuch verfuegt ueber 1500 EUR
		Waehrung w2 = Waehrung.BGN;
		s.einzahlen(betrag,w2);
		assertTrue(s.getKontostand() == 1602.2583762392437);		
	}

}
