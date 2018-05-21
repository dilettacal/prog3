/**
 * 
 */
package ratespielen;
import java.lang.Math;
import java.util.Scanner;
/**
 * @author Diletta Cal
 * 05.10.2017
 * @version
 *
 */
public class Ratespiel {
	//Konstante - Zahlenintervall
	public final static int MAX = 100;
	public final static int MIN = 1;
	
	/**
	 * 
	 * @param args
	 * 			nicht verwendet
	 */
	public static void main(String [] args){
		Scanner s = new Scanner(System.in);
		int rand  = (int )(Math.random() * 100 + 1);
		System.out.println("Raten Sie bitte die Zahl :-) ");
		int versuche = 0;
		int input = s.nextInt();
		
		while (true){
			System.out.println("Ihre Eingabe = " + input);
			if(input < rand){
				System.out.println("Eingabe ist kleiner als Zufallszahl!"
						+ " Versuchen Sie noch weiter: ");
				versuche++;
			}
			else if(input>rand){
				System.out.println("Eingabe ist groesser als Zufallszahl!"
						+ " Versuchen Sie noch weiter: ");
				versuche++;
			}
			else{
				System.out.println("Eureka! "
						+ "Sie haben die richtige Zufallszahl geraten: " + rand + 
						"! Sie haben " + versuche + " Versuche gebraucht.");
				s.close();
				break;
			}
			input = s.nextInt();
			
		}
	
	}

}
