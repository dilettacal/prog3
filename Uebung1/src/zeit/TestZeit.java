package zeit;

/**
 * @author Diletta Cal
 * 08.10.2017
 * @version
 *
 */
public class TestZeit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Zeit z1 = new Zeit();
		z1.ausgebendeutsch();
		System.out.println();
		z1.ausgebenEnglisch();

		Zeit testZeit1 = new Zeit();
		Zeit testZeit2 = new Zeit(15,35,58);
		int diff = testZeit2.differenz(testZeit1);
		System.out.println(diff + " Sekunden Unterschied");
		Zeit testZeit3 = new Zeit();
		diff = testZeit1.differenz(testZeit3);
		System.out.println(diff + " Sekunden Unterschied");
	}

}
