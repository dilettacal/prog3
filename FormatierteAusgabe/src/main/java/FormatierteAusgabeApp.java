/**
 * 
 */
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;


/**
 * Programm zur Manipulation von Eingaben/Ausgaben
 * @author Diletta Calussi
 * Matrikel-Nr.: 559842
 * 03.12.2017
 */
public class FormatierteAusgabeApp {
	/**
	 * Startet das Programm
	 * @param args
	 * 		wird nicht verwendet
	 */
	public static void main(String[] args) {
		long ganzeZahl = 0;
		double nachkommaZahl = 0;
		LocalDate heute = LocalDate.now();
		LocalTime jetzt = LocalTime.now();
		//Alternativ mit Scanner: Scanner input = new Scanner(System.in);
		
		//System.in vom Typ InputStream (byte) wird in einem InputStreamReader gekapselt
		//der wiederum in einen BufferedReader gekapselt wird
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter pw = new PrintWriter(new FileWriter("ausgabe.txt"), true)){
			
			System.out.println("Bitte geben Sie eine ganze Zahl ein: ");
			boolean input = true;
			while(input){
				try{
					ganzeZahl = Long.parseLong(br.readLine());
					input = false;
				} catch (NumberFormatException ne){
					System.out.println("Falsches Zahl-Format. "
							+ "Versuchen Sie nochmal:"
							+ System.getProperty("line.separator"));
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			input = true;
			System.out.println("Bitte geben Sie eine Fließkommazahl ein: ");
			while(input){
				try{
					nachkommaZahl = Double.parseDouble(br.readLine());
					input = false;
				} catch (NumberFormatException ne){
					System.out.println("Falsches Zahl-Format. "
							+ "Versuchen Sie nochmal:"
							+ System.getProperty("line.separator"));
				} catch(IOException e){
					e.printStackTrace();
				}
			}
			//1. Ausgabe ohne weitere Formatierung
			pw.printf("%d %n", ganzeZahl);
			//2. führende Nullen//String.valueOf(ganzeZahl).length()< 10)? "%010%d%n" :"%d%n"
			pw.printf("%010d%n",ganzeZahl);
			//3. Tausender-Trennzeichen
			pw.printf("%+,d%n", ganzeZahl); //US-Tausender-Trennzeichen
			//4. standard Formatierung für Fließkommazahlen
			pw.printf("%f%n", nachkommaZahl);
			//5. Zahl mit 2 Nachkommastellen
			pw.printf("%+.2f %n", nachkommaZahl);
			//6. wissenschaftliche Darstellung
			pw.printf("%e %n", nachkommaZahl);
			//7. Locale Format US 
			pw.printf(Locale.US, "%,.3f %n", nachkommaZahl);
			//8. Prozent Zeichen
			pw.printf("%% %n");
			//9. Datum (Saturday 2 December 2017)
			pw.printf("%1$tA %1$te %1$tB %1$tY %n", heute); //Locale.GERMANY
			//10. Datum im italienischen Format 
			pw.printf(Locale.ITALY, "%1$tA %1$te %1$tB %1$tY %n", heute);
			//11.Time im UK-Format
			pw.printf(Locale.UK, "%1$tl:%1$tM %tp %n", jetzt);		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
