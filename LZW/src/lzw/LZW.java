co/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guest
 */
public class LZW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String str="";

        List <String> list = new ArrayList <>();
        List <Integer> ind = new ArrayList <>();
        ind.add(65); ind.add(66); ind.add(65); ind.add(128); ind.add(128);
        ind.add(129); ind.add(131); ind.add(134);

        for (int i=0 ; i<128 ; i++)
        {
            list.add((char)i+"");
        }
      //  System.out.println (list.get(65));
      // initialized dictionary
      String dummy=list.get(ind.get(0));
      str+=dummy;
      String current;
       for (int i=1 ; i<ind.size(); i++)
       {
           if (ind.get(i)<list.size())
           {
                current = list.get(ind.get(i));
                 list.add(dummy+current.charAt(0));
                  str+=current;
                //  str+=" ";
                   dummy= list.get(ind.get(i));
                 //  System.out.println(current+"/n");
           }
           else
           {
               dummy = dummy +dummy.charAt(0);
               str+=dummy;
               list.add(dummy);
           }

       }
       System.out.println(str);
       System.out.println();
       System.out.println(list);
    }

}
