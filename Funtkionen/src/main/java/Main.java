import java.util.function.Function;

/**
 * @author Diletta Cal
 * 19.11.2017
 * @version
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 float fZero, gZero, hZero, kZero;
		    Function<Float, Float> f, g, h, k;

		    f = x -> (float) Math.pow(x, 2) - 5f;
		    g = x -> (float) (Math.exp(3 * x)) - 7f;
		    h = x -> ((5f - x) / (float) (Math.pow(x, 3) - 1f));
		    k = x -> (float) Math.pow(x, 2) + 1f; //komplexe Zahlen!
		    
		   //Funktion f
		    try {
		      fZero = Funktion.suche(f, 0, 10);
		      System.out.printf("Nullstelle in x = %.8f --> f(%.8f) = %.8f\n", fZero, fZero, f.apply(fZero));
		    } catch (ArithmeticException e) {
		      System.out.println(e.getMessage());
		    }
		    
		    //Funktion g
		    try {
		      gZero = Funktion.suche(g, 0, 1);
		      System.out.printf("Nullstelle in x = %.8f --> g(%.8f) = %.8f\n",gZero, gZero, g.apply(gZero));
		    } catch (ArithmeticException e) {
		      System.out.println(e.getMessage());
		    }
		    
		    //Funktion h
		    try {
		      hZero = Funktion.suche(h, 2, 50);
		      System.out.printf("Nullstelle in x = %.8f --> h(%.8f) = %.8f\n", hZero, hZero, h.apply(hZero));
		    } catch (ArithmeticException e) {
		      System.out.println(e.getMessage());
		    }

		    //Funktion k
		    try {
		      kZero = Funktion.suche(k, -100, 100);
		      System.out.printf("Nullstelle in x = %.8f --> k(%.8f) = %.8f\n", kZero, kZero, k.apply(kZero));
		    } catch (ArithmeticException e) {
		      System.out.println(e.getMessage());
		    }
		    
	}

}
