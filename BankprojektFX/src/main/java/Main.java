
	
import bankprojekt.oberflaecheFXML.KontoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * @author Diletta Cal
 * 09.01.2018
 * @version
 *
 */
public class Main extends Application {
	
	private KontoController con;

	@Override
	public void start(Stage primaryStage) {
		try {
			//System.out.println("WD " + System.getProperty("user.dir"));
			KontoController kc = new KontoController();
			con = kc;
			FXMLLoader loader = 
					new FXMLLoader(getClass().
						getResource("/bankprojekt/oberflaecheFXML/fxml/KontoOberflaeche.fxml")); ///C:/java/git/BankprojektFX/target/classes/bankprojekt/oberflaecheFXML/fxml/KontoOberflaeche.fxml
//			System.out.println(loader.getLocation().getPath());
			loader.setController(kc);
			Parent lc = loader.load();
			Scene scene = new Scene(lc);
			//scene.getStylesheets().add(getClass().getResource("bankprojekt/oberflaecheFXML/css/KontoOberflaeche.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void stop() throws Exception {
		con.modelValue();
	}
	
	
}
