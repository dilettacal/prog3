/**
 * 
 */
package bankprojekt.verarbeitung;

/**
 * Schnittstelle für Beobachter in der Bank
 * @author Diletta Cal
 * 19.12.2017
 *
 */
public interface Beobachter {
	
	/**
	 * Berichtet über Updates im subjekt
	 * @param subjekt das beobachtete Konto 
	 */
	public void update(Konto subjekt);

}
