package bankprojekt.oberflaecheFXML;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
/**
 * KontoController FXML-Project
 * @author Diletta Cal
 * 05.01.2018
 * @version
 */
public class KontoController {

	@FXML
	private Kunde kunde;
	@FXML
	private Girokonto kontoModel;
	@FXML
	private Text nummer;
	@FXML
	private Text stand;
	@FXML
	private CheckBox gesperrt;
	@FXML
	private TextArea adresse;
	@FXML
	private Spinner<?> betrag; //kann auch public sein - aber Eigenschaften private halten

	@FXML
	private Text meldung;

	public KontoController() {

	}

	@FXML
	public void initialize() throws GesperrtException {
		gesperrt.selectedProperty().bindBidirectional(kontoModel.gesperrtProperty());
		adresse.textProperty().bindBidirectional(kontoModel.getInhaber().adresseProperty());
	}

	/**
	 * Aktion für Einzahlen
	 * 
	 * @param betrag
	 *            der einzuzahlende Betrag
	 */
	public void einzahlen() {
		//double wert = (double) betrag.getValue(); //mvn cannot convert to double
		double wert = Double.valueOf(Double.parseDouble(""+ betrag.getValue()));
		if (!kontoModel.gesperrtProperty().get()) {
			kontoModel.einzahlen(wert);
			meldung.setText("Einzahlen erfolgreich!");
		} else {
			meldung.setText("Achtung - Konto: " + kontoModel.getGesperrtText() + " Einzahlen nicht möglich!");
		}

	}

	/**
	 * Aktion für Abheben
	 * 
	 * @param betrag
	 *            der abzuhebende Betrag kann sowohl gesperrtException als auch
	 *            false zurückgeben
	 * 
	 */
	public void abheben() {
		try {
			if (kontoModel.abheben((Double) betrag.getValue())) {
				meldung.setText(("Abheben erfolgreich"));
			} else {
				meldung.setText("Abheben nicht moeglich");
			}
		} catch (GesperrtException e) {
			meldung.setText("Achtung - Konto: " + kontoModel.getGesperrtText() + " Abheben nicht möglich!");
		}
	}

	public void modelValue() {
		System.out.println(kontoModel);
	}

}
