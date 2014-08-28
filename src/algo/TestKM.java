/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algo;

import Jama.Matrix;
import Jama.Matrix.*;
import java.io.PrintWriter;




/**
 *
 * @author Ecologic
 */
public class TestKM {
    
    public static void Preety2DArray(double a2[][]){
    
        int ROWS = 3;
        int COLS = 3;
        
        String output = "";   // Accumulate text here (should be StringBuilder).
        //... Print array in rectangular form using nested for loops.
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                output += " " + a2[row][col];
                
                System.out.println(output);
            }
            output += "\n";
        }
    
    }
    
    
    
     private  static void prettyPrintMatrix(String legend, Matrix matrix, 
      String[] documentNames, String[] words, PrintWriter writer) {
    writer.printf("=== %s ===%n", legend);
    writer.printf("%15s", " ");
    for (int i = 0; i < documentNames.length; i++) {
      writer.printf("%8s", documentNames[i]);
    }
    writer.println();
    for (int i = 0; i < words.length; i++) {
      writer.printf("%15s", words[i]);
      for (int j = 0; j < documentNames.length; j++) {
        
          writer.printf("%8.4f", matrix.get(i, j));
        
        
      }
      writer.println();
    }
    writer.flush();
  }
     
    
      private static Matrix PrintMatrix(double[][] vals,PrintWriter writer){
         System.out.println("===Matrix Printing===================");
         Matrix a = new Matrix(vals);
        for (int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                       writer.printf("\n %8.4f", a.get(i, j));
                 }
        }
        return a ;
      }
  
    
    
    
    public static void main(String args[]){
    
     
    double [][] costs = {{0.25,0,0},{0.25,0,0},{0,0.5,0.5}};
    
    Matrix mCosts = Matrix.constructWithCopy(costs);
    
    String legend ="===========================";
     String[] documentNames = {"D1","D2","D3"};
     String[] words ={"B","G1","G2"};
     
     
      prettyPrintMatrix(legend,mCosts, 
      documentNames, words, new PrintWriter(System.out, true));
     System.out.println("==============================");
     

     double output[][]= KuhnMunkres.computeCostAssignment(costs);
   
     Matrix mOutCosts = Matrix.constructWithCopy(output);
     prettyPrintMatrix(legend,mOutCosts, 
     documentNames, words, new PrintWriter(System.out, true));
     System.out.println("==============================");
     
     
     
        
    }
    
}
