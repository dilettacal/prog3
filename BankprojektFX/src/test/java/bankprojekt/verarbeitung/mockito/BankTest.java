/**
 * 
 */
package bankprojekt.verarbeitung.mockito;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import bankprojekt.verwaltung.mockito.Bank;
import bankprojekt.verwaltung.mockito.Kontofabrik;
import bankprojekt.verarbeitung.mockito.KontonummerNichtVorhandenException;


/**
 * JUnit-Test/Mockito fuer die Klasse Bank
 * @author Diletta Calussi
 * 31.10.2017 
 *
 */
public class BankTest {
	//getestete Klasse
	Bank bank;
	
	long ktnr1=0, ktnr1_test = 1; 
	long ktnr2=0, ktnr2_test = 1;
	long ktnr3=0;
	long gironr = 0;
	long sparnr =0;
	
	Kunde k1 = new Kunde();
	Kunde k2 = new Student("Diletta", "Calussi", "Kreuzberg", LocalDate.parse("1998-10-01"), "HTW", "Mathematik",
			LocalDate.parse("2030-12-31"), 4);
	Kunde k3 = new Kunde("Rainer Maria", "Rilke", "Prag", LocalDate.parse("1875-12-04"));
	Kunde k4 = new Student("Attelid", "Issulac", "Pankow", LocalDate.parse("1980-10-01"), "FU Berlin", "Literatur",
			LocalDate.parse("2023-12-31"), 2);
	
	//Mock-Objekte
	Kontofabrik girofabrik = Mockito.mock(Kontofabrik.class);
	Kontofabrik sparbuchfabrik = Mockito.mock(Kontofabrik.class);

	Konto konto1 = Mockito.mock(Konto.class);
	Konto konto2 = Mockito.mock(Konto.class);
	Konto konto3 = Mockito.mock(Konto.class);
	
	Konto test1 = Mockito.mock(Konto.class, Mockito.CALLS_REAL_METHODS);
	Kontofabrik testfabrik = Mockito.mock(Kontofabrik.class);

	
	
	@Before
	public void setUp() throws Exception {
		
		bank = new Bank(10070024);
		Mockito.when(girofabrik.erzeugen(ArgumentMatchers.isA(Kunde.class), ArgumentMatchers.eq(ktnr1)))
			.thenReturn(konto1);
		Mockito.when(sparbuchfabrik.erzeugen(ArgumentMatchers.isA(Kunde.class), ArgumentMatchers.eq(ktnr2)))
			.thenReturn(konto2);
		Mockito.when(testfabrik.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong()))
		.thenReturn(test1);
		
	}
	

	@Test
	public void testKorrektesGirokontoErstellen() {
		ktnr1 = bank.kontoErstellen(girofabrik, k1);
		Mockito.verify(girofabrik, Mockito.times(1))
		.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		assertTrue(ktnr1==1);
	}

	@Test
	public void testKorrektesSparbuchErstellen() {
		ktnr2 = bank.kontoErstellen(sparbuchfabrik, k2);
		Mockito.verify(sparbuchfabrik, Mockito.times(1))
			.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		assertTrue(ktnr2 ==1);
	}
	
	@Test
	public void testKontonummerVergabe() {
		ktnr1= bank.kontoErstellen(girofabrik, k1);
		ktnr2= bank.kontoErstellen(sparbuchfabrik, k2);
		long ktnr3 = bank.kontoErstellen(girofabrik, k3);
		//Verify 
		Mockito.verify(girofabrik, Mockito.times(2))
		.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		Mockito.verify(sparbuchfabrik, Mockito.times(1))
		.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		//Test der Vergabe
		assertEquals(1,ktnr1);
		assertEquals(2,ktnr2);
		assertEquals(3,ktnr3);
	}

	@Test 
	public void testGetKontostandVorhandenesKonto() throws KontonummerNichtVorhandenException {
		//Given: Konto ist erzeugt
		ktnr3= bank.kontoErstellen(testfabrik, Kunde.MUSTERMANN);
		//Mock-Einrichten
		Mockito.when(test1.getKontostand()).thenReturn(1000.0);
		
		//Methoden in Bank aufrufen
		double ktnstand = bank.getKontostand(ktnr3);
		
		//Überprüfung
		Mockito.verify(test1, Mockito.times(1)).getKontostand();
		
		//Test
		assertEquals(1000.00,ktnstand,1);
	}
	
	@Test
	public void testGetKontostandKontonummerNichtVorhanden() {
		long konto_nicht_vorhanden = 0;
		Mockito.reset(konto3);
		Mockito.doThrow(new KontonummerNichtVorhandenException(konto_nicht_vorhanden))
			.when(konto3).getKontostand();
		try {
			bank.getKontostand(konto_nicht_vorhanden);
			fail("Keine Exception aufgetreten!");
		}catch (KontonummerNichtVorhandenException e) {
			/*
			 * KontonummerNichtVorhandenException muss RuntimeException erweitern
			 * Sonst: "Checked exception is invalid for this method"
			 * Alternativ hier:
			 * throw new RuntimeException(e);
			 * und @Test(expected=RuntimeException.class)
			 */
		}
		Mockito.verifyZeroInteractions(konto3);
	}
	@Test
	public void testGetAlleKontonummern() {
		List<Long> erwartet = new ArrayList<>();
		long ko1=1;
		long ko2=2;
		long ko3=3;
		erwartet.add(ko1);
		erwartet.add(ko2);
		erwartet.add(ko3);
		ktnr1= bank.kontoErstellen(girofabrik, k1);
		ktnr2= bank.kontoErstellen(sparbuchfabrik, k2);
		long ktnr3 = bank.kontoErstellen(girofabrik, k3);
		
		Mockito.verify(girofabrik, Mockito.times(2))
		.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		Mockito.verify(sparbuchfabrik, Mockito.times(1))
		.erzeugen(ArgumentMatchers.any(Kunde.class), ArgumentMatchers.anyLong());
		
		assertTrue(erwartet.containsAll(bank.getAlleKontonummern()));

	}

	@Test
	public void testGeldEinzahlenNormalfall() throws KontonummerNichtVorhandenException {
		//ObserverBenachrichtigen in Bank auskommentiert
		ktnr3= bank.kontoErstellen(testfabrik, Kunde.MUSTERMANN);
		Mockito.doNothing().when(test1).notifyObservers();
		Mockito.doNothing().when(test1).einzahlen(ArgumentMatchers.anyDouble());
		bank.geldEinzahlen(ktnr3, 150.0);
		
		Mockito.verify(test1).einzahlen(ArgumentMatchers.anyDouble());	
		
	}

	
	@Test
	public void testGeldEinzahlenException() {
		Mockito.doThrow(new KontonummerNichtVorhandenException(ktnr1))
			.when(konto1).einzahlen(ArgumentMatchers.anyDouble());
		try{
			bank.geldEinzahlen(ktnr1, 500);
			fail("Keine Exception aufgetreten!");
		}catch(KontonummerNichtVorhandenException e) {}
		
	}
	
	@Test
	public void testGeldAbhebenNormalfall() throws KontonummerNichtVorhandenException, IllegalArgumentException, GesperrtException{
		//funktioniert nicht mit Mock ohne Option Mockito.CALLS_REAL_METHODS
		//funktioniert nicht mit Spionen - NullPointerException
		ktnr3 = bank.kontoErstellen(testfabrik, k1);
		Mockito.doNothing().when(test1).einzahlen(ArgumentMatchers.anyDouble());
		bank.geldEinzahlen(ktnr3, 1500.00);
		Mockito.verify(test1).einzahlen(ArgumentMatchers.anyDouble());
		
		bank.geldAbheben(ktnr3, 500.0);

		Mockito.verify(test1).abheben(ArgumentMatchers.anyDouble());
		Mockito.verify(test1).isGesperrt();
		Mockito.verify(test1).pruefeVorbedingungen(ArgumentMatchers.anyDouble());

	}

	//Alte Tests
	
//	@Test(expected=KontonummerNichtVorhandenException.class)
//	public void testGeldAbhebenException() throws KontonummerNichtVorhandenException{
//		bank.geldAbheben(12367L, 1000);
//	}
//
//	
//	@Test
//	public void testKontoLoeschen() throws KontonummerNichtVorhandenException {
//		List<Long> erwartet = new ArrayList<>();
//		erwartet.add(ktnr1);
//		erwartet.add(ktnr2);
//		erwartet.add(ktnr3);		
//		bank.kontoLoeschen(ktnr4);
//		assertTrue(erwartet.containsAll(bank.getAlleKontonummern()));
//	}
//
//	
//	@Test
//	public void testGetKontostand() throws KontonummerNichtVorhandenException {
//		bank.geldEinzahlen(ktnr2, 500);
//		assertTrue(bank.getKontostand(ktnr2)==500);
//	}
//	
//	@Test
//	public void testGetKontoStandException(){
//		try {
//			bank.geldEinzahlen(10, 100);
//			fail("Erwartete Exception nicht geworfen.");
//		} catch (KontonummerNichtVorhandenException e) {
//		}
//		
//	}
//	@Test
//	public void testGeldUeberweisen() throws KontonummerNichtVorhandenException, GesperrtException {
//		long tempk1, tempk2;
//		tempk1= bank.girokontoErstellen(k1);
//		tempk2 = bank.girokontoErstellen(k2);
//		bank.geldEinzahlen(tempk1, 2500);
//		bank.geldEinzahlen(tempk2, 1500);
//		bank.geldUeberweisen(tempk1, tempk2, 1000, "");
//		assertTrue(bank.getKontostand(tempk1)==1500 && bank.getKontostand(tempk2)==2500);
//		
//	}
//	
//	@Test
//	public void testBankKonstruktorException() {
//		try {
//			fehlschlagen = new Bank(-10);
//			fail("Parameter negativ");
//		} catch (IllegalArgumentException e) {
//
//		}
//	}
//	
//	@Test
//	public void testGetBankleittahl() {
//		int blz = 10070024;
//		assertEquals(blz, bank.getBankleittahl());
//	}


}
