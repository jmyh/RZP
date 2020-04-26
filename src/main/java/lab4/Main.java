package lab4;

import java.io.*;


public class Main {
    private final static String FILE_NAME="lab4.TestClass";

    public static void main(String[] args) {

        Main main=new Main();
        main.encryptClass();
        try {

            ClassLoader loader= new MyClassLoader();
            Class clazz= Class.forName("lab4.TestClass", true, loader);
            TrueStaticClass testClass = (TrueStaticClass) clazz.newInstance();
            System.out.println("\nMethod \"toString()\": "+testClass);
            System.out.print("Method \"print()\": ");
            testClass.print();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void encryptClass() {
//        System.out.println(getClass().getClassLoader().getResource("lab4/TestClass3.class"));
        byte[] byteArray=null;
        //reading test class
        try(InputStream inputStream=getClass().getClassLoader().getResourceAsStream("lab4/TestClass3.class");
            ByteArrayOutputStream byteStream=new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, len);
            }

            byteArray=byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //encryption test class
        byte[] encryptArray=new byte[byteArray.length];
        for (int i = 0; i < encryptArray.length; i++)
            encryptArray[i]= (byte) ((byteArray[i]+3)%256);

        //writing test class
        try(FileOutputStream fos=new FileOutputStream(getClass().getClassLoader().getResource("").getPath()+"lab4/TestClass3.class")) {
            fos.write(encryptArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

