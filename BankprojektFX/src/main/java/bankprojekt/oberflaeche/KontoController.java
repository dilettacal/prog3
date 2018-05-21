/**
 * 
 */
package bankprojekt.oberflaeche;

import java.time.LocalDate;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;

import bankprojekt.verarbeitung.Kunde;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for bank project
 * @author Diletta Cal
 * 01.01.2018
 * @version
 *
 */
public class KontoController extends Application{
	private Kunde kunde = new Kunde("Diletta", "Calussi", "Leipzigerstr. 77", 
			LocalDate.parse("1997-05-09"));
	private KontoOberflaeche ko;
	
	/**
	 * Das Model mit den aktuellen Daten des Kontos
	 */
	private Girokonto kontoModel = new Girokonto(kunde, 1001, 100);
	
	/**
	 * Das Hauptfenster der Anwendung
	 */
	private Stage stage;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		ko = new KontoOberflaeche(kontoModel, this);
		Scene scene = new Scene(ko, 500, 500);
		stage.setScene(scene);
		stage.setTitle("Kontoverwaltung");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Aktion für Einzahlen
	 * @param betrag der einzuzahlende Betrag
	 */
	public void einzahlen(double betrag){
		kontoModel.einzahlen(betrag);
		ko.setMeldung("Einzahlen erfolgreich");
	}
	
	/**
	 * Aktion für Abheben
	 * @param betrag der abzuhebende Betrag
	 * kann sowohl gesperrtException als auch false zurückgeben
	 * 
	 * 1. boolscher Wert zurückgaben
	 * 2. (besser) boolean geklappt = false;
	 * 			try{
	 * 				geklappt = this.konto.abheben(betrag);
	 * 			} catch (Gesperrt...) {
	 * 				geklappt = false; 
	 * 			}
	 * 		if(geklappt) ko.meldung("Abhebung hat nicht geklappt");
	 * 
	 */
	public void abheben(double betrag){
		try{
			if(kontoModel.abheben(betrag)){
				ko.setMeldung("Abheben erfolgreich");
			} else {
				ko.setMeldung("Abheben nicht möglich!");
			}
		} catch(GesperrtException e){
			ko.setMeldung("Achtung - Konto: " + kontoModel.getGesperrtText());
		}
	}

	@Override
	public void init() throws Exception {
		System.out.println("------ Werte vor Ausführung -------");
		System.out.println(kontoModel);
	}

	
	@Override
	public void stop() throws Exception {
		System.out.println("-------- Wert nach Ausführung -----------");
		System.out.println(kontoModel);
	}
	

}
