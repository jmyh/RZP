package lab4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyClassLoader extends ClassLoader{

    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        return loadClass(s,false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result= findClass(name);
        if (resolve)
            resolveClass(result);
        return result;
    }

    @Override
    protected Class<?> findClass(String fileName) throws ClassNotFoundException {

        if (fileName.toLowerCase().indexOf("truestatic")!=-1)
            return findSystemClass(fileName);

        File curFile=new File(
                getClass().getClassLoader().getResource("").getPath()+fileName.replace(".",File.separator)+".class");
        if (curFile.exists()) {
            byte[] encryptedClass=loadClassFromFile(fileName);
            System.out.println("Encrypted class:");
            System.out.println(new String(encryptedClass));
            byte[] decryptedClass=decryptClass(encryptedClass);
            System.out.println("\nDecrypted class:");
            System.out.println(new String(decryptedClass));
            return defineClass(fileName, decryptedClass, 0, decryptedClass.length);

        } else {
            return findSystemClass(fileName);
        }

    }

    private byte[] decryptClass(byte[] encryptedClass) {
        byte[] decryptedClass=new byte[encryptedClass.length];
        for (int i = 0; i < encryptedClass.length; i++) {
            decryptedClass[i]=(byte)((encryptedClass[i]-3)%256);
        }
        return decryptedClass;
    }

    private byte[] loadClassFromFile(String fileName) {

        fileName=fileName.replace(".","/");
        File file=new File(getClass().getClassLoader().getResource("").getPath()+fileName+".class");
        byte[] result=new byte[(int)file.length()];
        try(FileInputStream fis=new FileInputStream(file)) {
            fis.read(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
