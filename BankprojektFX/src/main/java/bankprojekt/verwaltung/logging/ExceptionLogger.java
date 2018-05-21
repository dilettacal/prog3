/**
 * 
 */
package bankprojekt.verwaltung.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasse zur Aufnahme der im Programm auftretenden Exceptions
 * Singleton-Pattern Uebung
 * @author Diletta Cal
 *
 */
public class ExceptionLogger{

	//Single-Ton Instanz
	private static ExceptionLogger instanz = new ExceptionLogger();
	private static final String LOG_PATH = "src/main/resources/ExceptionLog.txt";
	private BufferedWriter bufWriter;
	private File datei; 
	
	/**
	 * @return the datei
	 */
	public File getDatei() {
		return datei;
	}

	//Privater Konstruktor
	private ExceptionLogger(){
		//Erzeugungsdatum
		ZonedDateTime datumUndUhrzeit = ZonedDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");
		try {
			dateiÖffnen();
			String loggerDatei = "++++++++++++ Exception Logger +++++++++++++++" + System.lineSeparator()
			+ datumUndUhrzeit.format(format) + System.lineSeparator()
				+ "System: " + System.getProperty("os.name");
			bufWriter.write(loggerDatei);
			bufWriter.newLine();
			dateiSchliessen();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Liefert Instanz von Singleton-Objekt
	 * @return
	 */
	//Hier kann man die Instanz erzeugen
	public static ExceptionLogger getInstanz(){
		return instanz;
	}
	
	//Datei öffnen
	private void dateiÖffnen() throws IOException{
		datei = new File(LOG_PATH);
		if(datei.isFile())
			bufWriter = new BufferedWriter(new FileWriter(LOG_PATH, true));
		else throw new IOException("Ungültige Datei!");
	}
	
	//Datei schliessen
	private void dateiSchliessen() throws IOException{
		bufWriter.close();
	}
	
	/**
	 * Exception loggen
	 * @param e Exception
	 * @param beschreibung Beschreibung für LogDatei
	 */
	public void exceptionLoggen(Throwable exception){
		try{
			dateiÖffnen();
			//Datum der Exception
			ZonedDateTime datumUndUhrzeit = ZonedDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");
			//Infos zur Exception
			String exceptionBeschreibung = "-------------------------------" + System.lineSeparator();
			exceptionBeschreibung+= datumUndUhrzeit.format(format) + System.lineSeparator();
			exceptionBeschreibung+="Exception-Details: "+ System.lineSeparator() + 
					exception.getClass().getName() +": " + exception.getLocalizedMessage() + System.lineSeparator();
			exceptionBeschreibung+= getBeschreibung(exception) + System.lineSeparator();
			try {
				bufWriter.newLine();
				bufWriter.write(exceptionBeschreibung);
			} catch (IOException e1) {
				System.out.println("Logging Problem");
			} finally{
				dateiSchliessen();
			}
			
		} catch (Exception exc){
			System.out.println("Error");
		}
		
        
	}
	
	//String mit einigen Exception-Infos
	private String getBeschreibung(Throwable throwable){
		String stacktrace = "";
		StackTraceElement[] stackelements = throwable.getStackTrace();
		for (StackTraceElement e: stackelements){
			stacktrace+=e.getClassName() + "." + e.getMethodName()
			+" " + e.getLineNumber() + ": " + e.getFileName()+ System.lineSeparator();
		}
				 
		return stacktrace;
	}

	public void displayLog(){
		try (BufferedReader logReader=new BufferedReader(new FileReader(LOG_PATH))){
			String logLine;
			while((logLine = logReader.readLine())!=null){
				System.out.println(logLine);
			}
		} catch (IOException ex) {
			
		}

	}

	
	
}
