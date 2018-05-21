package brueche;
import java.util.Arrays;

/**
 * 
 * @author Diletta Cal
 * 08.10.2017
 *
 */
public class Main {

	/**
	 * Hauptprogramm zur Klasse Bruch.java
	 * @param args
	 * 		nicht verwendet
	 */
	public static void main(String[] args) {
		// Array von Bruechen
		Bruch[] brucharray = { new Bruch(-1, -8), new Bruch(10, 7), new Bruch(18, 18), new Bruch(22, 33),
				new Bruch(99, 109), new Bruch(25, 100), new Bruch(-12, 4), new Bruch(88, -44), new Bruch(-10, -40), new Bruch(0,18) };
		
		Bruch [] sortNachZaehler = brucharray.clone();
		//Start-Array:
		System.out.println("Array von Bruechen unsortiert:\n"+Arrays.toString(brucharray) + "\n");
		
		//Sortierung der natuerlichen Ordnung nach (compareTo)
		Arrays.sort(brucharray);
		System.out.println("Array aufsteigend sortiert:\n" +Arrays.toString(brucharray) + "\n");
		
		//Sortierung nach aufsteigenden Zaehlern
		Arrays.sort(sortNachZaehler, new ZaehlerVergleicher());
		System.out.println("Array nach aufsteigendem Zaehler sortiert:\n" +Arrays.toString(sortNachZaehler));
	
	}

}
