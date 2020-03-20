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
import java.util.Random;


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
        drawAxis(context);
        Random rand=new Random();
        context.setStroke(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        context.setLineWidth(3);
        context.strokeOval(xCircle,yCircle,radius,radius);
        context.setStroke(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        context.strokeRect(xRect,yRect,widthRect,heightRect);
        context.setStroke(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        context.strokeLine(0,c,500,k*500+c);

        context.setFill(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        context.fillOval(x,y,10,10);

    }

    private void drawAxis(GraphicsContext context) {
        context.setStroke(Color.BLACK);
        context.setLineWidth(3);
        context.strokeLine(3,3,500,5);
        context.strokeLine(3,3,3,500);

        for(int i=25;i<=500;i+=25) {
            context.strokeLine(i,0,i,6);
            context.fillText(String.valueOf(i),i,20);
            context.strokeLine(0,i,6,i);
            context.fillText(String.valueOf(i),10,i);
        }
        context.strokeLine(490,2,500,5);
        context.strokeLine(490,8,500,5);

        context.strokeLine(0,490,3,500);
        context.strokeLine(6,490,3,500);

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
