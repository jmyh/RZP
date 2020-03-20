package lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Group rootGroup=new Group();

        FXMLLoader loader= new FXMLLoader(getClass().getResource("lab3.fxml"));
        Parent content=loader.load();
        Controller controller=loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene=new Scene(content);

//        rootGroup.getChildren().add(content);

        primaryStage.setTitle("Lab 3 (--_--)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
