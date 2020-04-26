package newlab3;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    private static Scanner scan;
    private static DBService dbService;

    public static void main(String[] args) {
        dbService = new DBService("root", "toor");
        scan = new Scanner(System.in);
        try {
            while (true) {
                int mode = showMenu();
                switch (mode) {
                    case 1:
                        login();
                        break;
                    case 2:
                        registration();
                        break;
                    case 0:
                        dbService.closeConnection();
                        System.exit(0);
                    default:
                        System.err.println("Wrong option!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private static void registration() {
        while (true) {
            System.out.println("Enter login:");
            String login = scan.nextLine();
            System.out.println("Enter password:");
            String pass = scan.nextLine();
            long userID = dbService.addUser(login, calculateHash(pass));
            if (userID != -1) {
                System.out.println("Registration was success");
            } else {
                System.err.println("Registration was not success!");
            }
            break;
        }

    }

    private static void login() {
        System.out.println("Enter login:");
        String login = scan.nextLine();
        System.out.println("Enter password:");
        String passHash = calculateHash(scan.nextLine());
        User user = dbService.getUser(login);

        if (user == null) {
            System.err.println("User with login " + login + " does not exist!");
        } else {
            if (user.getHashPassword().equals(passHash)) System.out.println("Login was success");
            else System.err.println("Password is wrong!");
        }
    }

    private static int showMenu() {

        System.out.println("Select action:");
        System.out.println("\t1-Login");
        System.out.println("\t2-Registration");
        System.out.println("\t0-Exit");
        return Integer.parseInt(scan.nextLine());
    }

    private static String calculateHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(md.digest(password.getBytes())).toUpperCase();
    }
}
