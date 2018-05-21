/**
 * 
 */
package bankprojekt.verarbeitung;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit-Test fuer die Klasse Student
 * 
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 21.10.2017
 *
 */
public class StudentTest {
	
	Student student;

	@Before
	public void setUp() throws Exception {
		student = new Student();
		//Max, Musterstudent, findeKeinZimmer, 01.05.95, Uni, Phantasiefach, 23.10.20, 1
	}

	
	@Test
	public void testKonstruktorException() {
		try{
			student = new Student(null, null, null, null, "", "", "", -1);
			fail("Parameter gleich null");
		} catch (NullPointerException e){
			
		} catch (IllegalArgumentException e){
			
		}
		
	}

	
	@Test
	public void testGetUniName() {
		assertTrue(student.getUniName() == "Uni");
	}


	@Test
	public void testGetStudienfach() {
		String fach = student.getStudienfach();
		assertEquals("Phantasiefach", fach);
	}

	
	@Test
	public void testSetStudienende() {
		LocalDate toSet = LocalDate.parse("2017-10-23");
		student.setStudienende(toSet);
		assertEquals(toSet, student.getStudienende());
	}

	
	@Test
	public void testGetStudienende() {
		Student test2 = new Student();
		LocalDate ende = test2.getStudienende();
		assertEquals(LocalDate.now().plusYears(3), ende);
	}

	
	@Test
	public void testGetHochschulsemester() {
		int sem = student.getHochschulsemester();
		assertEquals(1,sem);
	}

	
	@Test
	public void testIstImmatrikuliertTrue() {
		assertTrue(student.istImmatrikuliert());
	}
	
	@Test
	public void testImmatrikuliertFalse(){
		Student nichtImmatrikuliert = new Student("Max", "Mustermann", "irgendwo", LocalDate.now(),
				"HTW", "Informatik", LocalDate.now(), -1);
		assertTrue(nichtImmatrikuliert.istImmatrikuliert() == false);
	}

	
	@Test
	public void testBescheinigungEintragenHappyPath() {
		student.bescheinigungEintragen(3);
		assertEquals(3, student.getHochschulsemester());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBescheinigungEintragenFehler(){
		student.bescheinigungEintragen(-1);
	}
	
	@Test
	public void testBeischeinigungEintragenNormalfall(){
		student.bescheinigungEintragen(4);
		assertTrue(student.getHochschulsemester()==4);
	}
	
	
}
