/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Guest
 */
public class Decoding {

    public String decode(String path) throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fis=new FileInputStream("C:\\Users\\Guest\\Desktop\\JPEG\\f2.txt");     
        Scanner scanner = new Scanner(fis);
        String compressed = scanner.nextLine();
        System.out.println(compressed);
        ArrayList<HuffmanTableRecord> huffmanTable = new ArrayList<>();

        while (true) {
            HuffmanTableRecord temp = new HuffmanTableRecord();
       
           String t = scanner.nextLine();
           if (t==null)
           {
               break;
           }
           else {
               temp.huffmanCode = t.trim();
           }
            
            System.out.println(temp.huffmanCode);
           t = scanner.nextLine();
           if (t==null)
           {
               break;
           }
           else {
               //temp.group.numOfZeros = Integer.parseInt(t.trim());
               System.out.println(t.trim());
           }
               //System.out.println(scanner.nextInt());
             //   temp.group.numOfZeros = scanner.nextInt();
              //  System.out.println(temp.group.numOfZeros);

        t = scanner.nextLine();
           if (t==null)
           {
               break;
           }
           else {
               //temp.group.numAfterZero = t.trim();
                System.out.println(t.trim());
           }

            huffmanTable.add(temp);
           // System.out.println(temp);
        }

        String decodedString = "";
        String temp = "";

        for (int i = 0; i < compressed.length(); i++) {
            temp += compressed.charAt(i);
            for (int j = 0; j < huffmanTable.size(); j++) {
                if (huffmanTable.get(i).huffmanCode.equals(temp)) {
                    for (int d = 0; d < huffmanTable.get(i).group.numOfZeros; d++) {
                        decodedString += '0';
                    }
                    if (!huffmanTable.get(i).group.numAfterZero.equals("")) {
                        String binStr = Integer.toBinaryString(Integer.parseInt(huffmanTable.get(i).group.numAfterZero));
                        // it is positive 
                        if (binStr.equals(compressed.substring(i + 1, i + binStr.length()))) {
                            decodedString += huffmanTable.get(i).group.numAfterZero;
                        } else {
                            decodedString += '-';
                            decodedString += huffmanTable.get(i).group.numAfterZero;
                        }
                        temp = "";
                        break;
                    }
                }

            }

        }

        return decodedString;
    }

}
