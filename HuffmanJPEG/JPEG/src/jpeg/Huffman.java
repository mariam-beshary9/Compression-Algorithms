/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import jpeg.Encoding.Pair;
import jpeg.HuffmanNode.MyComparator;


/**
 *
 * @author Mariam
 */
class HuffmanNode {

    double probability;
    Pair pair;
    String code;
    char flag;

    HuffmanNode left;
    HuffmanNode right;

    static double gcd(double a, double b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    // method to return LCM of two numbers 
    static double lcm(double a, double b) {
        return (a * b) / gcd(a, b);
    }

    static class MyComparator implements Comparator<HuffmanNode> {

        @Override
        public int compare(HuffmanNode x, HuffmanNode y) {
            double factor = lcm(1 / x.probability, 1 / y.probability);

            return (int) ((x.probability * (factor)) - (y.probability * (factor)));
        }
    }
}
class HuffmanTableRecord
{
    Pair group;
    String huffmanCode;
    @Override
        public String toString() {
          
            
            String s = "";
            s += group;
            s += " | ";
            s += huffmanCode;
       
            return s;
        }
}

public class Huffman {

    public static void assignCode(HuffmanNode root, String s) {

        if (root.left == null && root.right == null && root.flag != '-') {

            root.code = s;
           

        }
        if (root.left != null) {
            assignCode(root.left, s + "0");
        }
        if (root.right != null) {
            assignCode(root.right, s + "1");
        }
    }
    public void printTree (HuffmanNode root)
    {
        if (root.left == null && root.right == null && root.flag != '-')
        {
            System.out.println("pair "+root.pair+" with code "+root.code);
        }
         if (root.left != null)
            printTree ( root.left);
         if (root.right != null)
            printTree ( root.right);
        
    }
    

    public ArrayList<HuffmanTableRecord> runHuffman(ArrayList<Pair> distinctPairs, ArrayList<Double> probabilities) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(distinctPairs.size(), new MyComparator());

        for (int i = 0; i < distinctPairs.size(); i++) {

            HuffmanNode temp = new HuffmanNode();

            temp.pair = distinctPairs.get(i);
            temp.probability = probabilities.get(i);
            temp.flag = 'N';
            temp.left = null;
            temp.right = null;

            queue.add(temp);
        }
        HuffmanNode root = null;

        while (queue.size()>1)
        {
            HuffmanNode node1 = queue.peek();
            queue.poll();
            HuffmanNode node2 = queue.peek();
            queue.poll();
            
            HuffmanNode sumNode= new HuffmanNode();
            sumNode.left = node1;
            sumNode.right = node2;
            sumNode.probability = node1.probability+node2.probability;
            sumNode.flag='-';
            
            root=sumNode;
            queue.add(sumNode);
            
        }
        

       
        assignCode(root, "");
        //printTree(root);
        ArrayList<HuffmanTableRecord> HuffmanTable = new ArrayList<HuffmanTableRecord>();
        HuffmanTable=buildHuffmanTable( root, HuffmanTable );
        return HuffmanTable;
    }

    public ArrayList<HuffmanTableRecord> buildHuffmanTable(HuffmanNode root,ArrayList<HuffmanTableRecord> HuffmanTable )
    {
   
        if (root.left == null && root.right == null && root.flag != '-') {

            HuffmanTableRecord temp = new HuffmanTableRecord();
            temp.group=root.pair;
            temp.huffmanCode=root.code;
            HuffmanTable.add(temp);
        }
        if (root.left != null) {
            buildHuffmanTable(root.left,HuffmanTable);
        }
        if (root.right != null) {
            buildHuffmanTable(root.right, HuffmanTable);
        }
        
        
        return HuffmanTable;
    }

   
}

