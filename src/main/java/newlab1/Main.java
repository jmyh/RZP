package newlab1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map<Character, Integer> keys=new HashMap<>();
        Scanner scan=new Scanner(System.in);
        int numRooms=Integer.parseInt(scan.nextLine());
        char[] chars=scan.nextLine().toCharArray();
        int counter=0;

        for (int i = 0; i < chars.length; i+=2) {
            if(keys.get(chars[i])==null) keys.put(chars[i],1);
            else keys.put(chars[i],keys.get(chars[i])+1);

            if(keys.get(Character.toLowerCase(chars[i+1]))!=null && keys.get(Character.toLowerCase(chars[i+1]))!=0) {
                int currentCounter=keys.get(Character.toLowerCase(chars[i+1]));
                keys.put(Character.toLowerCase(chars[i+1]),currentCounter-1);
            } else {
                counter++;
            }
        }

        System.out.println(counter);
    }
}
