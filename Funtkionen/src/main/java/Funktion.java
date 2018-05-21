import java.util.function.Function;

/**
 * 
 */

/**
 * @author Diletta Cal
 * 19.11.2017
 * @version
 *
 */
public class Funktion {
	
	static final float GENAUIGKEIT = 0.01F; //Genauigkeit
	/**
	 * Suche nach der ersten Nullstelle einer gegebenen Funktion
	 * @param fkt die Funktion
	 * @param a Intervall
	 * @param b Intervall
	 * @return Ergebnis
	 * @throws ArithmeticException falls keine Nullstelle gefunden wird
	 */
	public static float suche(Function<Float, Float>fkt, float a, float b) throws ArithmeticException{
		float m = 0, yInA, yInB, yInM;
		yInA = fkt.apply(a);
		yInB = fkt.apply(b);
		if(yInA == 0) return a;
		if(yInB == 0) return b;
		while(Math.abs(a-b)> GENAUIGKEIT){
			m = (a+b)/2f; 
			yInM = fkt.apply(m);	
			if(yInM == 0) return m;
 			if(fkt.apply(a)*yInM < 0){
				b=m;					
			} else {
				a=m;
			}
		}
		//hier Intervall pruefen - wenn f flach ist kann f 0.1 schon überschneiden und erst eine Nullstelle in 50 haben
		if(fkt.apply(m) <= GENAUIGKEIT) return m;
		else throw new ArithmeticException("Keine Nullstelle gefunden!");
	}
	

}
