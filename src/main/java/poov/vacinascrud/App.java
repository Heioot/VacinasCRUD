package poov.vacinascrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;



public class App extends Application {

	@Override
	public void start(Stage stage) throws IOException {

		// Parent primaria = FXMLLoader.load(getClass().getResource("/fxml/TelaPrimaria.fxml"));
		// Scene scene = new Scene(primaria);
		// stage.setScene(scene);
		// stage.setTitle("Vacinas CRUD");
		// Image icon = new Image(getClass().getResourceAsStream("/images/vacinaicon.png"));
		// stage.getIcons().add(icon);
		// stage.show();

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaPrimaria.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("VacinasCRUD");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/vacinaicon.png")));
        stage.show();
	}

    public static void main(String[] args) {
		launch(args);
	}

}
