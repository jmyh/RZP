package misha1;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] keyBytes = Base64.getDecoder().decode(args[0]);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, key);

//        int value1;
//        int value2;
//        int value;
//
//        try (DataInputStream stream = new DataInputStream(new CipherInputStream(new FileInputStream(args[2]), aesCipher))) {
//            value1 = stream.readInt();
//            value2 = stream.readInt();
//        }

//        value =value1+value2;

        int[] value=new int[10];


        int minValue;

        try (DataInputStream stream = new DataInputStream(new CipherInputStream(new FileInputStream(args[2]), aesCipher))) {
            for (int i = 0; i < value.length; i++) {
                value[i]=stream.readInt();
            }
            minValue = Arrays.stream(value).min().getAsInt();

        }


        // ДОБАВЛЕНО>>
        keyBytes = Base64.getDecoder().decode(args[1]);
        key = new SecretKeySpec(keyBytes, "AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, key);
        // ДОБАВЛЕНО<<

        // Изменено
        try (DataOutputStream stream = new DataOutputStream(new CipherOutputStream(new FileOutputStream(args[3]), aesCipher))) {
//            stream.writeInt(value);
            stream.writeInt(minValue);
        }
    }
}
