package lab2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class App extends Application {

    static int xCircle;
    static int yCircle;
    static int radius;
    static int xRect;
    static int yRect;
    static int widthRect;
    static int heightRect;
    static int k;
    static int c;
    static int x;
    static int y;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        parseProperties();
        Group rootGroup=new Group();
        Scene scene=new Scene(rootGroup,500,500);
        Canvas canvas=new Canvas(500,500);
        rootGroup.getChildren().add(canvas);

        createElements(canvas.getGraphicsContext2D());

        primaryStage.setTitle("Lab2 (ಠ_ಠ)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createElements(GraphicsContext context) {
        context.setStroke(Color.DARKGREEN);
        context.setLineWidth(3);
        context.strokeOval(xCircle,yCircle,radius,radius);
        context.strokeRect(xRect,yRect,widthRect,heightRect);
        context.strokeLine(0,c,500,k*500+c);

        context.setFill(Color.RED);
        context.fillOval(x,y,10,10);
    }

    private void parseProperties() {
        Properties properties=new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/lab2.properties"));
        } catch (IOException e) {
            System.err.println("Property file is not correct!");
        }

        xCircle=Integer.parseInt(properties.getProperty("xCircle"));
        yCircle=Integer.parseInt(properties.getProperty("yCircle"));
        radius=Integer.parseInt(properties.getProperty("radius"));
        xRect=Integer.parseInt(properties.getProperty("xRect"));
        yRect=Integer.parseInt(properties.getProperty("yRect"));
        widthRect=Integer.parseInt(properties.getProperty("widthRect"));
        heightRect=Integer.parseInt(properties.getProperty("heightRect"));
        k=Integer.parseInt(properties.getProperty("k"));
        c=Integer.parseInt(properties.getProperty("c"));
        x=Integer.parseInt(properties.getProperty("x"));
        y=Integer.parseInt(properties.getProperty("y"));

    }
}
