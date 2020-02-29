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
class Boundries {

    double lowerRange;
    double upperRange;

    Boundries() {
        lowerRange = 0;
        upperRange = 0;
    }

    @Override
    public String toString() {

        return ("The lower: " + lowerRange + ", upper: " + upperRange);
    }
}

public class FloatingArithmeticCoding {

    static public ArrayList<Boundries> setRanges(ArrayList<Double> probabilities) {

        ArrayList<Boundries> bound = new ArrayList<>();

        for (int i = 0; i < probabilities.size(); i++) {
            Boundries b = new Boundries();
            b.lowerRange = 0;
            for (int j = 0; j < i; j++) {
                b.lowerRange += probabilities.get(j);
            }
            b.upperRange = b.lowerRange + probabilities.get(i);
            bound.add(b);
        }
        return bound;
    }

    public static void main(String[] args) {

        System.out.println("Enter the number or Operation:");
        System.out.println("1)Encode.");
        System.out.println("2)Decode.");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        if (choice == 1) {
            Encoding encoder = new Encoding();

            System.out.println("Enter the text to be encoded.");
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();
            ArrayList<Character> symbols = encoder.getSymbols(text);
            ArrayList<Double> Probabilities = encoder.getPropability(text, symbols);
            //System.out.println(Probability);
            ArrayList<Boundries> ranges = setRanges(Probabilities);

            //for (int i=0 ; i<ranges.size() ; i++)
            //{
            //    System.out.println("For Symbol " +symbols.get(i) + " : "+ranges.get(i));
            //}
            double lower = 0;
            double upper=1;

            double range ;
            for (int i = 0; i < text.length(); i++) {
                
                range = upper - lower;
                upper = lower + (range * (ranges.get(symbols.indexOf(text.charAt(i))).upperRange));
                lower += (range * (ranges.get(symbols.indexOf(text.charAt(i))).lowerRange));
                System.out.println(lower+" "+ upper);
            }
            System.out.println((upper+lower)/2);
        }
        else if (choice ==2)
        {
            int n ;
            
            System.out.println("Enter how many symbols.");
            n= s.nextInt();
            System.out.println("Enter the symbols.");
            ArrayList<String> symbols = new ArrayList <>();
            for (int i=0 ; i<n ; i++)
            {   
                symbols.add(s.next());
            }
            
            System.out.println("Enter the probability of each symbol.");
            ArrayList<Double> probabilities = new ArrayList <>();
            
            for (int i=0 ; i<n ; i++)
            {   
                probabilities.add(s.nextDouble());
            }
            ArrayList<Boundries> ranges = setRanges(probabilities);
            System.out.println("Enter the size of the text to generate.");
            int textSize = s.nextInt();
            System.out.println("Enter the floating number you want to decode.");
            double FloatingNumber = s.nextDouble();

            String decodedStr="";
            double low=0.0;
            double up =1.0;
            
            double value;
            double range;
            for (int i=0 ;i<textSize ; i++)
            {   value = (FloatingNumber - low)/(up-low);
                for (int j=0 ;j<ranges.size() ; j++)
                {
                    if((value>=ranges.get(j).lowerRange) && (value<=ranges.get(j).upperRange) )
                    {
                        decodedStr+=symbols.get(j);
                       // System.out.println(value);
                        range = up - low;
                       // System.out.println(low +" "+ranges.get(j).lowerRange);
                         up= low +(range*ranges.get(j).upperRange);
                        // System.out.println(up +" "+low + " "+ranges.get(j).upperRange);
                         low+=(range*ranges.get(j).lowerRange);
                         //System.out.println(low +" "+up + " "+ranges.get(j).lowerRange);
                        // System.out.println(low +" "+up + " ");
                        
                     //   System.out.println(low +" "+up + " ");
                  
                        break;
                    }
                }
            }
            System.out.println(decodedStr);
        }
         
    }

}
