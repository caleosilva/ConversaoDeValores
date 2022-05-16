package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private double x,y = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/telaPrincipal.fxml"));
			Scene scene = new Scene(root);
			
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
	        primaryStage.initStyle(StageStyle.TRANSPARENT);
	        primaryStage.setResizable(false);	        
	        
	        // Habilita mover a janela
	        root.setOnMousePressed(event -> {
	            x = event.getSceneX();
	            y = event.getSceneY();
	        });
	        
	        root.setOnMouseDragged(event -> {
	            primaryStage.setX(event.getScreenX() - x);
	            primaryStage.setY(event.getScreenY() - y);
	        });
			

			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
