package bankprojekt.oberflaeche;

import bankprojekt.verarbeitung.Konto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 * Eine Oberfläche für ein einzelnes Konto. Man kann einzahlen
 * und abheben und sperren und die Adresse des Kontoinhabers 
 * ändern
 * @author Doro
 * @author Diletta Cal
 *
 */
public class KontoOberflaeche extends BorderPane {
	
	/* Provided by instructor */
	private KontoController controller;
	private Konto kontoModel;
	
	
	private Text ueberschrift;
	private GridPane anzeige;
	private Text txtNummer;
	/**
	 * Anzeige der Kontonummer
	 */
	private Text nummer;
	private Text txtStand;
	/**
	 * Anzeige des Kontostandes
	 */
	private Text stand;
	private Text txtGesperrt;
	/**
	 * Anzeige und Änderung des Gesperrt-Zustandes
	 */
	private CheckBox gesperrt;
	private Text txtAdresse;
	/**
	 * Anzeige und Änderung der Adresse des Kontoinhabers
	 */
	private TextArea adresse;
	/**
	 * Anzeige von Meldungen über Kontoaktionen
	 */
	private Text meldung;
	private HBox aktionen;
	/**
	 * Auswahl des Betrags für eine Kontoaktion
	 */
	private Spinner<Double> betrag;
	/**
	 * löst eine Einzahlung aus
	 */
	private Button einzahlen;
	/**
	 * löst eine Abhebung aus
	 */
	private Button abheben;
	
	/**
	 * erstellt die Oberfläche
	 */
	public KontoOberflaeche(Konto k, KontoController kc)
	{
		this.kontoModel = k;
		this.controller = kc;
		
		ueberschrift = new Text("Ein Konto verändern");
		ueberschrift.setFont(new Font("Sans Serif", 25));
		BorderPane.setAlignment(ueberschrift, Pos.CENTER);
		this.setTop(ueberschrift);
		
		anzeige = new GridPane(); //erstmals Alignment= top left and padding=0
		anzeige.setPadding(new Insets(20)); //Abstand zwischen Elementen vom GridpPane-Rand: 20
		anzeige.setVgap(10); //Vertikaler Abstand zwischen Elementen im Grid
		anzeige.setAlignment(Pos.CENTER); //Grid-Positionierung im GRIDPANE
		
		txtNummer = new Text("Kontonummer:");
		txtNummer.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtNummer, 0, 0); //Spalte 0, Reihe 0 -> Text
		nummer = new Text();
		nummer.setFont(new Font("Sans Serif", 15));
		
		//setHalignment ist eine static void Methode aus GridPane
		GridPane.setHalignment(nummer, HPos.RIGHT); //Horizontale Positionierung vom Child nummer
		anzeige.add(nummer, 1, 0);
		
		txtStand = new Text("Kontostand:");
		txtStand.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtStand, 0, 1);
		stand = new Text();
		stand.setFont(new Font("Sans Serif", 15));
		
		GridPane.setHalignment(stand, HPos.RIGHT);
		anzeige.add(stand, 1, 1);
		
		txtGesperrt = new Text("Gesperrt: ");
		txtGesperrt.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtGesperrt, 0, 2);
		gesperrt = new CheckBox();
		
		GridPane.setHalignment(gesperrt, HPos.RIGHT);
		anzeige.add(gesperrt, 1, 2);
		
		txtAdresse = new Text("Adresse: ");
		txtAdresse.setFont(new Font("Sans Serif", 15));
		anzeige.add(txtAdresse, 0, 3);
		adresse = new TextArea();
		adresse.setPrefColumnCount(25);
		adresse.setPrefRowCount(2);
		
			
		GridPane.setHalignment(adresse, HPos.RIGHT);
		anzeige.add(adresse, 1, 3);
		
		meldung = new Text("Willkommen lieber Benutzer");
		meldung.setFont(new Font("Sans Serif", 15));
		meldung.setFill(Color.RED);
		anzeige.add(meldung,  0, 4, 2, 1); //2: # Spalten, 1: # Reihen
		
		//this = BorderPane --> im Zentrum kommt das GridPane
		this.setCenter(anzeige);
		
		aktionen = new HBox();
		aktionen.setSpacing(10);
		aktionen.setAlignment(Pos.CENTER);
		
		betrag = new Spinner<>(10, 100, 50, 10);
		aktionen.getChildren().add(betrag);
		einzahlen = new Button("Einzahlen");
		
		aktionen.getChildren().add(einzahlen);
		abheben = new Button("Abheben");
		
		aktionen.getChildren().add(abheben);
		
		this.setBottom(aktionen);
		
		/* 
		 * Implementation tasks (Uebung 12): Set Bindings 
		 * 
		 */
		
		
		//Anzeige der Kontonummer:
		//man kann das auch über die Property machen, ist auch so korrekt
		nummer.setText(kontoModel.getKontonummerFormatiert());

		//Anzeige des Kontostandes
		//Hier Bindung unbedingt nötig, da Kontostand sich immer wieder ändert
		//concat ist freiwillig
		stand.textProperty().bind(kontoModel.kontostandProperty().asString().concat(" €"));
		/*
		 * 
		 * Meldung kann so erfolgen:
		 */
		//stand.fillProperty().bind(Bindings.when(this.kontoModel.negativerKontostandPropert()).then(Color.RED).otherwise(Color.GREEN));
		//Ansonsten Listener einbauen
		
		
		//Gesperrt-Text anpassen je nach Gesperrt-Zustand:
		gesperrt.selectedProperty().bindBidirectional(kontoModel.gesperrtProperty());	
		//Anpassung des Texts
		txtGesperrt.textProperty().bind(Bindings.concat("Konto ist gesperrt: ", kontoModel.gesperrtProperty()));
		//"Konto gesperrt: ", kontoModel.gesperrtProperty())
		//Adresse anzeigen
		adresse.textProperty().bindBidirectional(kontoModel.getInhaber().adresseProperty());
		
		//Aktion für einzahlen
		einzahlen.setOnAction(e -> 	controller.einzahlen(Double.parseDouble(""+betrag.getValue())));
		
		//Aktion für abheben
		//Oberflaeche muss nicht exception behandeln, eventuell verfügt sie über eine Methode um anzuzeige, ob das geklappt hat
		abheben.setOnAction(e -> controller.abheben(Double.parseDouble("" + betrag.getValue())));		
	}
	
	/**
	 * Ändert den Text der Meldung
	 * @param text der neue Text
	 */
	protected void setMeldung(String text){
		this.meldung.setText(text);
	}
}
