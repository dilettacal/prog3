package zensurenspiegel;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 10.10.2017
 * Klasse Notenspiegel zur Bearbeitung von Noten
 *
 */
public class Notenspiegel {
	
	/**
	 * 
	 * @param args
	 * 			wird nicht verwendet
	 */
	public static void main (String [] args){
		Scanner s = new Scanner(System.in);
		int note;
		int [] anzahlNoten = null;
		String eingabe;
		System.out.print("Willkommen im Programm 'Notenspiegel'\n"
				+ " Geben Sie hier die maximale Note fuer Ihren Spiegel ein: ");
		try {
			note = s.nextInt();
			anzahlNoten = notenspiegel(note+1);
		} catch (InputMismatchException e) {
			System.out.println("Das war keine gueltige Zahl");
			System.out.println("Wollen Sie nochmal probieren? Dann druecken Sie die Taste 'j' oder 'J'."
					+ " Um das Programm zu verlassen, druecken Sie die Taste Z");
			eingabe = s.next();
			if (eingabe.charAt(0) == 'z' || eingabe.charAt(0) == 'Z')
				s.close();
		}
		if(anzahlNoten != null)
			notenAusgabe(anzahlNoten);

	}

	
	/**
	 * Die Methode bearbeitet den Notenspiegel
	 * @param max die maximale Note
	 * @return int-Array mit den Notenvorkommen
	 */
	private static int[] notenspiegel(int max){		
		int [] anzahlNoten = new int[max];
		final int OBERGRENZE = anzahlNoten.length -1, UNTERGRENZE = 1, STOP = max;
		int note = 0;
		Scanner input = new Scanner(System.in);
		System.out.print("Ihr Notensystem: " + UNTERGRENZE + " bis " + OBERGRENZE + ".\n"
				+ "Geben Sie bitte im Folgenden Ihre Noten ein. "
				+ " Um das Programm zu verlassen, geben Sie bitte die Zahl " + STOP + " ein.\n");
		do {
			System.out.print("Ihre Note: ");
			try{
				note = input.nextInt();
				System.out.println();
				//Falls Note die Grenze ueberschreitet wird eine neue Eingabe gefordert
				if((note > OBERGRENZE && note != STOP) || note < UNTERGRENZE){
					System.out.println("Die Note liegt ausserhalb der erlaubten Grenzen 1-5");
					continue;
				} 
				
				else if(note == STOP)
					break;
				
				else {
					anzahlNoten[note-1]++;
				}
			} catch(InputMismatchException e){ //falls Note nicht numerisch ist
				System.out.println("Das war keine gueltige Note.");
			}
		} while (note != STOP);
		input.close();
		return anzahlNoten;
	}
	
	/**
	 * Die Methode gibt die Anzahl der eingegebenen Noten aus
	 * @param noten das auszugebende Array
	 */
	private static void notenAusgabe(int [] noten){
		System.out.print("Uebersicht der Noten: \n");
		for(int i = 1; i < noten.length; i++){
			System.out.println("--------------------");
			System.out.println("Note " + i + ": "+ noten[i-1] + " mal");
		}
	}
}
