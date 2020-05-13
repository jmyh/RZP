package newlab4;

import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.util.Scanner;

public class Main {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static Scanner scan=new Scanner(System.in);


    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Algorithm encrypAlg, hashingAlg=null;
        Signature signature;
        KeyPairGenerator keyPairGen;

        encrypAlg=getAlgorithm("Select the encryption algorithm:\n\t1) DSA\n\t2) RSA")==1?Algorithm.DSA:Algorithm.RSA;
        if(encrypAlg!=Algorithm.DSA) hashingAlg=getAlgorithm("Select the hashing algorithm:\n\t1) MD5\n\t2) SHA-1")==1?Algorithm.MD5:Algorithm.SHA_1;

        System.out.println("Enter message for digest signature:");
        String message=scan.nextLine();

        if(encrypAlg==Algorithm.DSA) {
            keyPairGen = KeyPairGenerator.getInstance("DSA");
            signature = Signature.getInstance("SHA1withDSA");
        }else {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            if (hashingAlg==Algorithm.SHA_1) {
                signature = Signature.getInstance("SHA1withRSA");
            } else {
                signature = Signature.getInstance("MD5withRSA");
            }
        }

        keyPairGen.initialize(1024);
        KeyPair keyPair=keyPairGen.genKeyPair();
        PrivateKey privateKey=keyPair.getPrivate();
        PublicKey publicKey=keyPair.getPublic();

        byte[] bytesSignature=createSignature(signature,privateKey,message);
        verifySignature(signature,publicKey,bytesSignature,message);

    }

    private static void verifySignature(Signature signature, PublicKey publicKey, byte[] bytesSignature, String message) throws InvalidKeyException, SignatureException {
        System.out.println("\nVERIFYING OF SIGNATURE");
        signature.initVerify(publicKey);
        System.out.println("Public key: " + publicKey);
        signature.update(message.getBytes());
        System.out.println("Message bytes: " + DatatypeConverter.printHexBinary(message.getBytes()).toUpperCase());
        boolean verified = signature.verify(bytesSignature);
        if (verified) System.out.println("Result verifying: " + ANSI_BOLD + ANSI_GREEN + verified + ANSI_RESET);
        else System.out.println("Result verifying: " + ANSI_BOLD + ANSI_RED + verified + ANSI_RESET);
    }

    private static byte[] createSignature(Signature signature, PrivateKey privateKey, String message) throws SignatureException, InvalidKeyException {
        System.out.println("\nCREATION OF SIGNATURE");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        System.out.println("Message bytes: " + DatatypeConverter.printHexBinary(message.getBytes()).toUpperCase());
        byte[] bytesSignature = signature.sign();
        System.out.println("Signature bytes: " + DatatypeConverter.printHexBinary(bytesSignature).toUpperCase());
        System.out.println("Creation of signature was success!");
        return  bytesSignature;
    }

    private static int getAlgorithm(String text) {
        System.out.println(text);
        int selectedA=Integer.parseInt(scan.nextLine());
        if(selectedA==1 || selectedA==2) {
            return selectedA;
        }
        else {
            System.err.println("Wrong option");
            System.exit(-1);
        }
        return -1;
    }
}
