/**
 * 
 */
package bankprojekt.verarbeitung;

/**
 * @author Diletta Cal
 * 28.10.2017
 * @version
 *
 */
@SuppressWarnings("serial")
public class KontonummerNichtVorhandenException extends Exception {
	
	public KontonummerNichtVorhandenException(long nummer){
		super("Das Konto mit der Kontonummer " + nummer + " existiert nicht.");
	}

}
