package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		initRootLayout(primaryStage);
	}

	@Override
	public void stop() throws Exception {
	}

	public void initRootLayout(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BidirecTable");

		try {

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCellView.fxml"));
			rootView = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootView);

			this.primaryStage.setScene(scene);
			this.primaryStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}