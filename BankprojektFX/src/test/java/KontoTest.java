import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

public class KontoTest {

	private Konto g;
	private Konto s;
	private Konto spyG;
	private Konto spyS;
	
	@Before
	public void setUp(){
		g = new Girokonto(Kunde.MUSTERMANN, 101010, 500);
		s = new Sparbuch(Kunde.MUSTERMANN, 10505);
		spyG = Mockito.spy(g);
		spyS = Mockito.spy(s);
		
	}
	
//	Mockito.doNothing().when(spion).releaseItem();
//	  Mockito.doReturn(34).when(spion).getPrice();
	/*SpyObject real = new SpyObject();
    SpyObject spy = Mockito.spy(real);
    SpyObject anotherSpy = Mockito.spy(real);
     
    Mockito
      .when(anotherSpy.sayHello())
      .thenReturn("Goodbye!");
 
    System.out.println("1: " + real.sayHello());
    System.out.println("2: " + spy.sayHello());
    System.out.println("3: " + anotherSpy.sayHello());
*/
	@Test
	public void testAbhebenNormalFallGirokonto() {
		
		
	}
	
	@Test
	public void testAbhebenNormalFallSparbuch(){
		
	}
	
	@Test
	public void testAbhebenIllegalArgumentException() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAbhebenGesperrtException(){
	}

}
