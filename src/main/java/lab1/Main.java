package lab1;

import java.util.Random;
import java.util.Scanner;



public class Main {
    private final static int NUMBER_ELEMENTS=20;
    private final static int START=-10;

    static int xCircle;
    static int yCircle;
    static int radius;
    static int x1Rect;
    static int y1Rect;
    static int x2Rect;
    static int y2Rect;
    static int k;
    static int c;
    static int x;
    static int y;


    public static void main(String[] args) {
        init();

        //conditions for circle
        if(Math.pow(x-xCircle,2)+Math.pow(y-yCircle,2)>Math.pow(radius,2))
            System.out.println("Outside circle");
        else if (Math.pow(x-xCircle,2)+Math.pow(y-yCircle,2)<Math.pow(radius,2))
            System.out.println("Inside circle");
        else System.out.println("On circle");

        //conditions for rectangle
        if(x1Rect<x && x2Rect>x && y1Rect<y && y2Rect>y)
            System.out.println("Inside rectangle");
        else if (!(x1Rect<=x && x2Rect>=x && y1Rect>=y && y2Rect<=y))
            System.out.println("Outside rectangle");
        else System.out.println("On rectangle");

        //conditions for line
        if(-k*x-c+y>0) System.out.println("To the left of the line");
        else if(-k*x-c+y<0) System.out.println("To the right of the line");
        else System.out.println("On the line");
    }

    private static void init() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter flag (1-random values, 0-customize): ");
        boolean random = (scan.nextInt() == 1) ? true : false;
        if (!random) {
            System.out.print("Enter coordinates of the center of the circle: ");
            xCircle = scan.nextInt();
            yCircle = scan.nextInt();
            System.out.print("Radius: ");
            radius = scan.nextInt();
            System.out.print("Enter coordinates of the rectangle: ");
            x1Rect = scan.nextInt();
            y1Rect = scan.nextInt();
            x2Rect = scan.nextInt();
            y2Rect = scan.nextInt();
            System.out.print("Enter coefficient of line: ");
            k = scan.nextInt();
            c = scan.nextInt();
            System.out.print("Enter coordinates of point: ");
            x = scan.nextInt();
            y = scan.nextInt();
        } else {
            Random rand = new Random();

            xCircle = rand.nextInt(NUMBER_ELEMENTS) + START;
            yCircle = rand.nextInt(NUMBER_ELEMENTS) + START;
            System.out.println("Coordinates of the center of the circle: " + xCircle + " " + yCircle);
            radius = rand.nextInt(NUMBER_ELEMENTS/2);
            System.out.println("Radius: " + radius);
            x1Rect = rand.nextInt(NUMBER_ELEMENTS) + START;
            y1Rect = rand.nextInt(NUMBER_ELEMENTS) + START;
            x2Rect = rand.nextInt(NUMBER_ELEMENTS) + START;
            if (x2Rect<x1Rect) x2Rect+=x1Rect;
            y2Rect = rand.nextInt(NUMBER_ELEMENTS) + START;
            if(y2Rect<y2Rect) y2Rect+=y1Rect;
            System.out.println("Coordinates of the rectangle: " + x1Rect + " " + y1Rect + " " + x2Rect + " " + y2Rect);
            k = rand.nextInt(NUMBER_ELEMENTS) + START;
            c = rand.nextInt(NUMBER_ELEMENTS) + START;
            System.out.println("Enter coefficient of line: " + k + " " + c);
            x = rand.nextInt(NUMBER_ELEMENTS) + START;
            y = rand.nextInt(NUMBER_ELEMENTS) + START;
            System.out.println("Coordinates of point: " + x + " " + y);
        }
    }
}
