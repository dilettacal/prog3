import org.mockito.Mockito;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.KontonummerNichtVorhandenException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.GirokontoFabrik;
import bankprojekt.verwaltung.Kontofabrik;

public class Testing {

	public static void main(String[] args) throws KontonummerNichtVorhandenException {
//		Kontofabrik giro = Mockito.mock(Kontofabrik.class);
//		Kontofabrik spar = Mockito.mock(Kontofabrik.class);
//		
//		System.out.println(giro.getClass().getSimpleName());
//		System.out.println(spar);

		Bank b1 = new Bank(191919);
		Kontofabrik k1 = new GirokontoFabrik();
		Konto ko = k1.erzeugen(Kunde.MUSTERMANN, 10);
		System.out.println(ko);
		
		long nr = b1.kontoErstellen(k1, Kunde.MUSTERMANN);
		System.out.println(b1.getKontostand(nr));
		System.out.println(b1.getAlleKonten());
		
		
		
	}

}
