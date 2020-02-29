/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Guest
 */
public class Encoding {

    static class Pair {

        int numOfZeros;
        String numAfterZero;
        boolean negFlag;

        public Pair() {
            numOfZeros = 0;
            numAfterZero = "0";
            negFlag = false;
        }

        @Override
        public String toString() {
            if (numAfterZero.equals('0' + "")) {
                return "EOB";
            } else {
                String s = "";
                s += numOfZeros;
                s += "/";
                s += numAfterZero;
                //  if (negFlag) {
                //     s += '-';
                //}
                return s;
            }
        }

    }

    public static boolean absContain(ArrayList<Pair> list, Pair p) {
        for (int i = 0; i < list.size(); i++) {
            if (((list.get(i).numAfterZero.equals(p.numAfterZero)) && (list.get(i).numOfZeros == p.numOfZeros))) {
                return true;
            }
        }
        return false;
    }

    public static boolean absEqual(Pair p1, Pair p2) {
        if ((p1.numAfterZero.equals(p2.numAfterZero)) && (p1.numOfZeros == p2.numOfZeros)) {
            return true;
        }
        return false;
    }

    public String encode(String path) throws IOException {

        String string = "";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            string += bufferedReader.readLine();

        } catch (FileNotFoundException e) {
            System.out.println("Could not open the file.");
        }

        // now we separate the string 
        ArrayList<Pair> pairs = new ArrayList<>();
        int zeroCounter = 0;
        for (int i = 0; i < string.length(); i++) {
            Pair p = new Pair();
            if (string.charAt(i) == '-') {
                p.negFlag = true;
                p.numOfZeros = zeroCounter;
                p.numAfterZero = string.charAt(i + 1) + "";
                i++;
                zeroCounter = 0;
                pairs.add(p);
            } else if (string.charAt(i) == '0') {
                zeroCounter++;
                if (i == string.length() - 1) {
                    p.negFlag = true;
                    p.numOfZeros = zeroCounter - 1;
                    p.numAfterZero = " ";
                    pairs.add(p);

                }
            } else {
                p.negFlag = false;
                p.numOfZeros = zeroCounter;
                p.numAfterZero = string.charAt(i) + "";
                pairs.add(p);
                zeroCounter = 0;
            }
        }
        // System.out.print(pairs);

        // to get the probability 
        ArrayList<Pair> distinctPairs = new ArrayList<>();

        for (int i = 0; i < pairs.size(); i++) {

            if (!absContain(distinctPairs, pairs.get(i))) {
                distinctPairs.add(pairs.get(i));
                System.out.println(pairs.get(i));
            }
        }

        ArrayList<Double> probabilities = new ArrayList<>();
        for (int i = 0; i < distinctPairs.size(); i++) {
            double counter = 0;
            for (int j = 0; j < pairs.size(); j++) {
                if (absEqual(distinctPairs.get(i), pairs.get(j))) {
                    counter++;
                }
            }
            probabilities.add((counter / pairs.size()));

        }
        System.out.println(pairs);
        System.out.println(distinctPairs);
        System.out.println(probabilities);
        Huffman h = new Huffman();
        ArrayList<HuffmanTableRecord> huffmanTable = h.runHuffman(distinctPairs, probabilities);
        System.out.println(huffmanTable);

        String encodedString = "";
        // now we put the huffman code then the binary rep.
        for (int i = 0; i < pairs.size(); i++) {
            for (int j = 0; j < huffmanTable.size(); j++) {
                if (absEqual(pairs.get(i), huffmanTable.get(j).group)) {
                    encodedString += huffmanTable.get(j).huffmanCode;
                    if (!pairs.get(i).numAfterZero.equals(" ")) {
                        if (pairs.get(i).negFlag) {

                            String tmp = Integer.toBinaryString(Integer.parseInt(pairs.get(i).numAfterZero));
                            String newTemp = "";
                            for (int k = 0; k < tmp.length(); k++) {
                                if (tmp.charAt(k) == '0') {
                                    newTemp += '1';
                                } else if (tmp.charAt(k) == '1') {
                                    newTemp += '0';
                                }
                            }
                            encodedString += newTemp;
                        } else {
                            encodedString += Integer.toBinaryString(Integer.parseInt(pairs.get(i).numAfterZero));
                        }
                    } 

                    break;
                }
            }

        }
        //System.out.println(encodedString);
        FileWriter myWriter = new FileWriter("C:\\Users\\Guest\\Desktop\\JPEG\\f2.txt");
        String str = "";
        str += encodedString;
        str += System.getProperty("line.separator");
        for (int i = 0; i < huffmanTable.size(); i++) {
            str += huffmanTable.get(i).huffmanCode;
            str += System.getProperty("line.separator");
            str += huffmanTable.get(i).group.numOfZeros;  // this is int 
            str += System.getProperty("line.separator");
            str += huffmanTable.get(i).group.numAfterZero;
            str += System.getProperty("line.separator");

        }
        System.out.println(str);
        myWriter.write(str);
        myWriter.close();

        return encodedString;
    }

}
