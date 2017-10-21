package brueche;
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 05.10.2017
 * JUnit Tests fuer Klasse Bruch.java
 *
 */
public class BruchTest {
	
	Bruch b, c, erg;
	int n,z;
	
	@Test
	public void testParameterKonstruktorStandard(){
		b = new Bruch(1,4); // 1/4
		String erwartet ="1/4";
		String objekt = b.toString();
		assertTrue(erwartet.compareTo(objekt) == 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParameterKonstruktorNennerGleichNull(){
		b = new Bruch(3,0);	
	}
	
	@Test
	public void testParameterKonstruktorExc(){
		try{
			b = new Bruch(8,0);
		} catch (IllegalArgumentException e){
			assertEquals("Nenner darf nicht gleich null sein!", e.getMessage());
		}
		
	}
	
	@Test
	public void testGetNenner(){
		b = new Bruch(4,5);
		n = 5;
		assertTrue(n == b.getNenner());
	}
	
	@Test
	public void testGetZaehler(){
		b = new Bruch(4,5);
		z = 4;
		assertTrue(z ==b.getZaehler());
	}
	
	@Test
	public void testMultipliziereStandard(){
		b = new Bruch(1,5);
		c = new Bruch(2,6);
		z = 2;
		n = 30;
		erg = b.multiplizieren(c);
		assertEquals(n==erg.getNenner()&&z==erg.getZaehler(), true);			
	}
	
	@Test
	public void testMultipliziereNurEinNegativesGlied(){
		b = new Bruch(-1,6);
		c = new Bruch(4,5);
		z = -4;
		n = 30;
		erg = b.multiplizieren(c);
		assertEquals(true, n==erg.getNenner()&&z==erg.getZaehler());	
	}
	
	@Test
	public void testMultiplizierenBeideNennerNegativ(){
		b = new Bruch(-4,5);
		c = new Bruch(-1,4);
		z = 4;
		n = 20;
		erg = b.multiplizieren(c);		
		assertEquals(true, n==erg.getNenner()&&z==erg.getZaehler());		
	}
	
	@Test
	public void testMultiplizierenBeideNennerEinZaehlerNegativ(){
		b = new Bruch(-4,5);
		c = new Bruch(-1,-4);
		z = 4;
		n = -20;
		erg = b.multiplizieren(c);
		assertEquals(true, n==erg.getNenner()&&z==erg.getZaehler());		
	}
	
	
	@Test
	public void testAusrechnenStandard(){
		b = new Bruch(33,66);
		assertTrue(b.ausrechnen()==0.5);
	}
	
	@Test 
	public void testAusrechnenNegativeWerte(){
		b = new Bruch(33,-66);
		c = new Bruch(-33,-66);
		assertEquals(true, b.ausrechnen()==-0.5 && c.ausrechnen()==0.5 );
	}
	
	@Test
	public void testKuerzenStandard(){
		b = new Bruch(33,66);
		b.kuerzen();
		assertEquals(true, (b.getZaehler()==1 && b.getNenner() == 2));
	}
	
	@Test
	public void testKuerzenTeilerfremd(){
		b = new Bruch(7,5);
		b.kuerzen();
		assertEquals(true, (b.getZaehler()==7 && b.getNenner() ==5));
		
	}
	@Test
	public void testKuerzenNegativ(){
		b = new Bruch(35,-7);
		b.kuerzen();
		assertEquals(true,(b.getZaehler()==-5 && b.getNenner()==1));
	}
	
	@Test
	public void testKehrwertStandard(){
		b = new Bruch(6,7);
		n = 6; z = 7;
		b.kehrwert();
		assertEquals(true, n==b.getNenner()&&z==b.getZaehler());		
	}
	
	@Test(expected=ArithmeticException.class)
	public void testKehrwertZaehlerGleichNenner(){
		b = new Bruch(0,9);
		b.kehrwert();
	}
	
	@Test
	public void testDividierenStandard(){
		b = new Bruch(4,5);
		c = new Bruch(4,5);
		erg = b.dividieren(c);
		assertEquals(true, erg.getNenner()==20 && erg.getZaehler()==20);		
		
	}
	
	@Test
	public void testDividierenZaehlerGleichNull(){
		b = new Bruch(5, 6);
		c = new Bruch(0, 2);
		try{
			b.dividieren(c);
		} catch (ArithmeticException e){
			assertEquals("Operation nicht gueltig. Nenner darf nicht null sein!", e.getMessage());
		}
	}
	
	@Test
	public void testAusgabe(){
		b = new Bruch(7,5);
		assertTrue(b.toString().compareTo("7/5")==0);
	}
	
	@Test
	public void testAusgabeEinGliedNegativ(){
		b = new Bruch(-7,5);
		assertTrue(b.toString().compareTo("-7/5")==0);
	}
	
	@Test
	public void testAusgabeBeideGliederNegativ(){
		b = new Bruch(-7,-44);
		assertTrue(b.toString().compareTo("7/44")==0);
	}
	
	@Test
	public void testAusgabeNennerGleichEins(){
		b = new Bruch(-7,1);
		assertTrue(b.toString().compareTo("-7")==0);
	}
	@Test
	public void testBruchVergleicher(){
		b = new Bruch(7,5);
		c = new Bruch(9,10);
		assertTrue(b.compareTo(c)>0);
	}
	
}
