/**
 * 
 */
package bankprojekt.verarbeitung;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.Kontofabrik;

/**
 * JUnit-Test fuer die Klasse Bank
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 31.10.2017 
 *
 */
public class BankTest {
	Bank bank, fehlschlagen;
	long ktnr1, ktnr1_test = 1, ktnr2, ktnr2_test = 1, ktnr3, ktnr4;
	Kunde k1 = new Kunde();
	Kunde k2 = new Student("Diletta", "Calussi", "Kreuzberg", LocalDate.parse("1998-10-01"), "HTW", "Mathematik",
			LocalDate.parse("2030-12-31"), 4);
	Kunde k3 = new Kunde("Rainer Maria", "Rilke", "Prag", LocalDate.parse("1875-12-04"));
	Kunde k4 = new Student("Attelid", "Issulac", "Pankow", LocalDate.parse("1980-10-01"), "FU Berlin", "Literatur",
			LocalDate.parse("2023-12-31"), 2);
	
	Kontofabrik girofabrik = Mockito.mock(Kontofabrik.class);
	Kontofabrik sparbuchfabrik = Mockito.mock(Kontofabrik.class);
	
	Kontofabrik girofabrik_fail = Mockito.mock(Kontofabrik.class);
	Kontofabrik sparbuchfabrik_fail = Mockito.mock(Kontofabrik.class);

	Konto konto1 = Mockito.mock(Konto.class);
	Konto konto2 = Mockito.mock(Konto.class);
	
	
	@Before
	public void setUp() throws Exception {
		//Bank initialisieren
		bank = new Bank(10070024);
		Mockito.when(girofabrik.erzeugen(Matchers.isA(Kunde.class), Matchers.eq(ktnr1)))
			.thenReturn(konto1);
		Mockito.when(sparbuchfabrik.erzeugen(Matchers.isA(Kunde.class), Matchers.eq(ktnr2)))
			.thenReturn(konto2);
	}

	@Test
	public void testKorrektesGirokontoErstellen() {
		ktnr1 = bank.kontoErstellen(girofabrik, k1);
		assertEquals(ktnr1, ktnr1_test);
	}

	@Test
	public void testKorrektesSparbuchErstellen() {
		ktnr2 = bank.kontoErstellen(sparbuchfabrik, k2);
		assertEquals(ktnr2, ktnr2_test);
	}
	
	@Test
	public void testKontonummerVergabe() {
		Bank mockBank = Mockito.mock(Bank.class);
		Mockito.when(mockBank.kontoErstellen(Matchers.isA(Kontofabrik.class), Matchers.isA(Kunde.class)))
		.thenAnswer(new Answer<Long>() {
			long nr;
			@Override
			public Long answer(InvocationOnMock arg0) throws Throwable {
				return ++nr;
			}
		});
		
		assertEquals(1,mockBank.kontoErstellen(girofabrik, k1));
		assertEquals(2,mockBank.kontoErstellen(sparbuchfabrik, k2));
		assertEquals(3,mockBank.kontoErstellen(girofabrik, k3));
	}

	@Test 
	public void testGetKontostandVorhandenesKonto() {
		
	}
	@Test
	public void testGetAlleKontonummern() {
		List<Long> erwartet = new ArrayList<>();
		erwartet.add(ktnr1);
		erwartet.add(ktnr2);
		erwartet.add(ktnr3);
		erwartet.add(ktnr4);
		assertTrue(erwartet.containsAll(bank.getAlleKontonummern()));

	}

	@Test
	public void testGeldEinzahlenNormalfall() throws KontonummerNichtVorhandenException {
		bank.geldEinzahlen(ktnr1, 1500);
		assertTrue(bank.getKontostand(ktnr1)==1500);
	}

	
	@Test(expected=KontonummerNichtVorhandenException.class)
	public void testGeldEinzahlenException() throws KontonummerNichtVorhandenException {
		bank.geldEinzahlen(123456L, 500);
	}
	
	@Test
	public void testGeldAbhebenNormalfall() throws KontonummerNichtVorhandenException{
			bank.geldEinzahlen(ktnr1, 1500);		
			bank.geldAbheben(ktnr1, 500);		
			assertTrue(bank.getKontostand(ktnr1)==1000);
		
	}
	
	
	@Test(expected=KontonummerNichtVorhandenException.class)
	public void testGeldAbhebenException() throws KontonummerNichtVorhandenException{
		bank.geldAbheben(12367L, 1000);
	}

	
	@Test
	public void testKontoLoeschen() throws KontonummerNichtVorhandenException {
		List<Long> erwartet = new ArrayList<>();
		erwartet.add(ktnr1);
		erwartet.add(ktnr2);
		erwartet.add(ktnr3);		
		bank.kontoLoeschen(ktnr4);
		assertTrue(erwartet.containsAll(bank.getAlleKontonummern()));
	}

	
	@Test
	public void testGetKontostand() throws KontonummerNichtVorhandenException {
		bank.geldEinzahlen(ktnr2, 500);
		assertTrue(bank.getKontostand(ktnr2)==500);
	}
	
	@Test
	public void testGetKontoStandException(){
		try {
			bank.geldEinzahlen(10, 100);
			fail("Erwartete Exception nicht geworfen.");
		} catch (KontonummerNichtVorhandenException e) {
		}
		
	}
	@Test
	public void testGeldUeberweisen() throws KontonummerNichtVorhandenException, GesperrtException {
		long tempk1, tempk2;
		tempk1= bank.girokontoErstellen(k1);
		tempk2 = bank.girokontoErstellen(k2);
		bank.geldEinzahlen(tempk1, 2500);
		bank.geldEinzahlen(tempk2, 1500);
		bank.geldUeberweisen(tempk1, tempk2, 1000, "");
		assertTrue(bank.getKontostand(tempk1)==1500 && bank.getKontostand(tempk2)==2500);
		
	}
	
	@Test
	public void testBankKonstruktorException() {
		try {
			fehlschlagen = new Bank(-10);
			fail("Parameter negativ");
		} catch (IllegalArgumentException e) {

		}
	}
	
	@Test
	public void testGetBankleittahl() {
		int blz = 10070024;
		assertEquals(blz, bank.getBankleittahl());
	}


}
