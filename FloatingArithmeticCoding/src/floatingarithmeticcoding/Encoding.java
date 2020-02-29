/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package floatingarithmeticcoding;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Guest
 */


public class Encoding {

     public ArrayList<Character> getSymbols(String word) {
        ArrayList<Character> distinctChar = new ArrayList<>();
        distinctChar.add(word.charAt(0));
        for (int i = 1; i < word.length(); i++) {
            if (!distinctChar.contains(word.charAt(i))) {
                distinctChar.add(word.charAt(i));
            }
        }
        return distinctChar;
    }

     public ArrayList<Double> getPropability(String word, ArrayList<Character> distinctChar) {
        // here we get symbols not repeated.

        // here we calculate frequency for every symbol
        ArrayList<Integer> frequencies = new ArrayList<>();

        for (int i = 0; i < distinctChar.size(); i++) {
            int counter = 0;
            for (int j = 0; j < word.length(); j++) {
                if (distinctChar.get(i).equals(word.charAt(j))) {
                    counter++;
                }
            }
            frequencies.add(counter);
        }
        int sum = 0;
        for (Integer frequencie : frequencies) {
            sum += frequencie;
        }
        ArrayList<Double> probabilities = new ArrayList<>();
        for (int i = 0; i < frequencies.size(); i++) {
            probabilities.add(frequencies.get(i) / (sum * 1.0));
        }

        return probabilities;
    }


}
